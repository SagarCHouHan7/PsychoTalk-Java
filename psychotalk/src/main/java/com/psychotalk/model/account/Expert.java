package com.psychotalk.model.account;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "expert")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Expert extends Account{

    private String fullName;
    private String email;

    @Column(name="about", length = 4000)
    private String about;
    private String gender;

    @Temporal(TemporalType.DATE)
    private Date dob;

    @Temporal(TemporalType.DATE)
    private LocalDate joiningDate;

    @ElementCollection
    @CollectionTable(
            name = "expert_qualification",
            joinColumns = @JoinColumn(name = "expert_id")
    )
    @Column(name = "qualification")
    private List<String> qualification;

    private String experience;
    private double fees;
    private String address;
    private String phoneNo;

    @Column(nullable = true)
    private double rating;
    private boolean isVerified=false;
    private boolean isVerificationSubmitted=false;
    private String documentPath;

}
