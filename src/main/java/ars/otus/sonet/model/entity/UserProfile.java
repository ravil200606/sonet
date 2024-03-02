package ars.otus.sonet.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class UserProfile {

    private Integer id;

    private String userId;

    private String firstName;

    private String secondName;

    private LocalDate birthDate;

    private String biography;

    private String city;
}
