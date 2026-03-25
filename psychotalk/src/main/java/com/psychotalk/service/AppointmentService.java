package com.psychotalk.service;

import com.psychotalk.dto.appointmentDto.AppointmentRequestDto;
import com.psychotalk.dto.appointmentDto.AppointmentResDtoForExpert;
import com.psychotalk.dto.appointmentDto.AppointmentResDtoForUser;
import com.psychotalk.dto.expertDto.ExpertProfileDto;
import com.psychotalk.dto.userDto.UserProfileDto;
import com.psychotalk.exception.customExceptions.*;
import com.psychotalk.model.Appointment;
import com.psychotalk.model.enums.AppointmentStatus;
import com.psychotalk.model.account.Expert;
import com.psychotalk.model.account.User;
import com.psychotalk.model.enums.PaymentStatus;
import com.psychotalk.repository.AppointmentRepo;
import com.psychotalk.repository.ExpertRepo;
import com.psychotalk.repository.UserRepo;
import com.psychotalk.security.SecurityUtils;
import com.psychotalk.service.Utils.CurrentRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private ExpertRepo expertRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CurrentRoleService currentRoleService;

    @Transactional
    public AppointmentResDtoForExpert markStatus(Long id, AppointmentStatus status) {

        Appointment appointment = appointmentRepo.findByIdForUpdate(id)
                .orElseThrow(AppointmentNotFoundException::new);
        Expert expert = expertRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(()->new ExpertNotFoundException("you can only update your own appointments"));

        if (!Objects.equals(appointment.getExpert().getId(), expert.getId())) {
            throw new AppointmentStatusViolationException("You can only update your own appointments");
        }

            switch (status) {

                case COMPLETED -> {
                    if (appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED)
                        throw new AppointmentStatusViolationException("Appointment already completed");

                    if (appointment.getAppointmentStatus() != AppointmentStatus.SCHEDULED)
                        throw new AppointmentStatusViolationException("Appointment must be scheduled first");
                    if (appointment.getPaymentStatus() != PaymentStatus.SUCCESS)
                        throw new AppointmentStatusViolationException("cannot mark completed until payment get success full");


                    LocalDateTime endTime = appointment.getAppointmentTime().plusMinutes(appointment.getDurationInMinutes());
                    if (endTime.isAfter(LocalDateTime.now()))
                        throw new AppointmentStatusViolationException("Cannot mark completed before session ends");

                    appointment.setAppointmentStatus(AppointmentStatus.COMPLETED);
                }

                case CONFIRMED -> {
                    if (appointment.getAppointmentStatus() == AppointmentStatus.PAYMENT_PENDING)
                        throw new AppointmentStatusViolationException("Appointment already confirmed");

                    if (appointment.getAppointmentStatus() != AppointmentStatus.REQUESTED)
                        throw new AppointmentStatusViolationException("can only confirm newly requested appointments");

                    appointment.setAppointmentStatus(AppointmentStatus.PAYMENT_PENDING);
                }

                case CANCELLED -> {
                    if (appointment.getAppointmentStatus() == AppointmentStatus.CANCELLED)
                        throw new AppointmentStatusViolationException("Appointment already cancelled");

                    if (appointment.getAppointmentStatus() == AppointmentStatus.PAYMENT_PENDING
                            || appointment.getAppointmentStatus() == AppointmentStatus.REQUESTED) {
                        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
                    } else {
                        throw new AppointmentStatusViolationException("can only cancel requested or payment pending appointments");
                    }
                }

                default -> throw new AppointmentStatusViolationException("Unsupported status transition");
            }

            Appointment saved = appointmentRepo.save(appointment);
            return mapToAppointmentResDtoForExpert(saved);

    }


    public String requestAppointment(AppointmentRequestDto dto) {
        Appointment appointment = new Appointment();

        Expert expert = expertRepo.findById(dto.getExpertId())
                .orElseThrow(ExpertNotFoundException::new);

        if(!checkSlotAvailability(dto)) throw new TimeSlotNotAvailableException("its a rare case but the requested time slot is not available");

        appointment.setExpert(expert);
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setReason(dto.getReason());
        appointment.setDurationInMinutes(dto.getDurationInMinutes());
        User user = currentRoleService.getCurrentUser();
        appointment.setUser(user);

        int fees = expert.getFees();
        if(dto.getDurationInMinutes() > 15){
            double chargeForOneMinute = (double) fees /15;
            fees = (int)Math.round(chargeForOneMinute * dto.getDurationInMinutes());
        }
        appointment.setAmountInPaise(fees*100);
        appointment.setCurrency(expert.getCurrency());

        Appointment saved = appointmentRepo.save(appointment);

        return "status : success";
    }

    public boolean checkSlotAvailability(AppointmentRequestDto appointmentRequest) {
        int c = appointmentRepo.checkSlotAvailability(
                appointmentRequest.getAppointmentTime(),
                appointmentRequest.getDurationInMinutes(),
                appointmentRequest.getExpertId()
                );
        return c == 0;
    }

    public AppointmentResDtoForUser user_cancelMyAppointment(Long id) {
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(()-> new AppointmentNotFoundException("appointment not found"));

        //state validation
        if(!appointment.getAppointmentStatus().canUserCancel())
            throw new AppointmentStatusViolationException("You cannot cancel this appointment at its current stage");
        //ownership check
        if(!SecurityUtils.getCurrentUsername().equals(appointment.getUser().getUsername()))
                throw new AccessDeniedException("you can only update your own fields");

        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);

        return mapToAppointmentResponseDtoForUser(appointmentRepo.save(appointment));

    }

    public List<AppointmentResDtoForUser> getByUserId() {
        User user = currentRoleService.getCurrentUser();
        List<Appointment> list = appointmentRepo.findAppointmentsByUserId(user.getId());
        return list.stream().map(this::mapToAppointmentResponseDtoForUser).toList();
    }

    public AppointmentResDtoForUser mapToAppointmentResponseDtoForUser(Appointment appointment){
        AppointmentResDtoForUser dto = new AppointmentResDtoForUser();
        BeanUtils.copyProperties(appointment, dto);
        Expert expert = appointment.getExpert();
        ExpertProfileDto expertProfileDto = mapToExpertProfileDto(expert);
        dto.setExpert(expertProfileDto);
        return dto;

    }

    private ExpertProfileDto mapToExpertProfileDto(Expert expert){
        ExpertProfileDto dto = new ExpertProfileDto();
        BeanUtils.copyProperties(expert , dto);
        dto.setAbout("");
        dto.setQualifications(expert.getQualification());
        dto.setRole("EXPERT");
        return dto;
    }

    public List<AppointmentResDtoForExpert> getByExpertId() {
        Expert expert = currentRoleService.getCurrentExpert();
        List<Appointment> list = appointmentRepo.findByExpertId(expert.getId());
        return list.stream().map(this::mapToAppointmentResDtoForExpert).toList();

    }

    public AppointmentResDtoForExpert mapToAppointmentResDtoForExpert(Appointment appointment){
        AppointmentResDtoForExpert dto = new AppointmentResDtoForExpert();
        BeanUtils.copyProperties(appointment, dto);
        User user = appointment.getUser();
        UserProfileDto userDto = new UserProfileDto();
        BeanUtils.copyProperties(user, userDto);
        dto.setUser(userDto);
        return dto;
    }



}
