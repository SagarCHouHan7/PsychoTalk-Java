package com.psychotalk.dto.expertDto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ExpertVerificationRequestDto {
    private String fullName;
    private String email;
    private String about;
    private String gender;
    private Date dob;
    private List<String> qualifications;
    private String experience;
    private int fees;
    @JsonSetter(nulls = Nulls.SKIP)
    private String currency = "INR";
    private String address;
    private String phoneNo;
}
