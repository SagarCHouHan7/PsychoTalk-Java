package com.psychotalk.dto.userDto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserProfileDto {
    private Long id;
    private Date dob;
    private String gender;
    private String username;
}
