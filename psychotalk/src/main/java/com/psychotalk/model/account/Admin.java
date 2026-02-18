package com.psychotalk.model.account;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "admin")
public class Admin extends Account{

    @Temporal(TemporalType.DATE)
    private Date joiningDate;
}
