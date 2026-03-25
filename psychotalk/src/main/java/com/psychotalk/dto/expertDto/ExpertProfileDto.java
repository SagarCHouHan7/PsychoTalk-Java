package com.psychotalk.dto.expertDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ExpertProfileDto {
    private Long id;
    private String username;
    private String role;
    private String fullName;
    private String about;
    private String gender;
    private Date dob;
    private List<String> qualifications;
    private String experience;
    private int fees;
    private String currency;
    private String address;
    private boolean isVerified;
    private  boolean isVerificationSubmitted;
    private LocalDate joiningDate;
}
