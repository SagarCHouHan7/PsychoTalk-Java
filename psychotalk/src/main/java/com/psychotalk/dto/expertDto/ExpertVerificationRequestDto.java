package com.psychotalk.dto.expertDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Service
public class ExpertVerificationRequestDto {
    private String fullName;
    private String email;
    private String about;
    private String gender;
    private Date dob;
    private List<String> qualifications;
    private String experience;
    private double fees;
    private String address;
    private String phoneNo;
}
