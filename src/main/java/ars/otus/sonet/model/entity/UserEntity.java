package ars.otus.sonet.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserEntity {

    private Integer id;

    private String userId;

    private String password;
}
