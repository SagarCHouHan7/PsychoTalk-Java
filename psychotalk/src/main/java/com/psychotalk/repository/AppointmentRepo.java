package com.psychotalk.repository;

import com.psychotalk.model.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository <Appointments, Integer>{

    @Query(value =  "SELECT * FROM appointments WHERE expert_id=:expertId AND appointment_time BETWEEN (:appointmentTime - interval 1 hour) AND (:appointmentTime + interval 1 hour) ",
            nativeQuery = true)
    Appointments searchIfTimeAvailable(@Param("appointmentTime") Date appointmentTime , @Param("expertId") Integer expertId);

    @Query("SELECT a from Appointments a WHERE user.id=:userId")
    List<Appointments> getAppointmentsBYUserId(@Param("userId") Integer userId);

    @Query("SELECT a from Appointments a WHERE expert.id=:expertId")
    List<Appointments> getAppointmentsBYExpertId(@Param("expertId") Integer expertId);
}
