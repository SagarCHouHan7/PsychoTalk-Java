package com.psychotalk.repository;

import com.psychotalk.model.Appointment;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface AppointmentRepo extends JpaRepository <Appointment, Long>{



    @Query("SELECT a FROM Appointment a WHERE a.user.id=:id")
    List<Appointment> findAppointmentsByUserId(@Param("id") Long id);

    @Query("SELECT a FROM Appointment a WHERE a.expert.id=:id")
    List<Appointment> findByExpertId(@Param("id") Long id);

    @Query("SELECT a FROM Appointment a WHERE a.orderId=:orderId")
    Optional<Appointment> findByOrderId(@Param("orderId") String orderId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Appointment a WHERE a.id = :id")
    Optional<Appointment> findByIdForUpdate(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM appointments a WHERE " +
            "a.expert_id = :expertId " +
            "AND a.appointment_status != 'CANCELLED' " +
            "AND (" +
            "    :appointmentTime < DATE_ADD(a.appointment_time, INTERVAL a.duration_in_minutes MINUTE) " +
            "    AND DATE_ADD(:appointmentTime, INTERVAL :durationInMinutes MINUTE) > a.appointment_time " +
            ")",
            nativeQuery = true)
    int checkSlotAvailability(
            @Param("appointmentTime") LocalDateTime appointmentTime,
            @Param("durationInMinutes") int durationInMinutes,
            @Param("expertId") Long expertId
    );


//    @Query(value =  "SELECT * FROM appointments WHERE expert_id=:expertId AND appointment_time BETWEEN (:appointmentTime - interval 1 hour) AND (:appointmentTime + interval 1 hour) ",
//            nativeQuery = true)
//    Appointments searchIfTimeAvailable(@Param("appointmentTime") Date appointmentTime , @Param("expertId") Integer expertId);
//
//    @Query("SELECT a from Appointments a WHERE user.id=:userId")
//    List<Appointments> getAppointmentsBYUserId(@Param("userId") Integer userId);
//
//    @Query("SELECT a from Appointments a WHERE expert.id=:expertId")
//    List<Appointments> getAppointmentsBYExpertId(@Param("expertId") Integer expertId);
}
