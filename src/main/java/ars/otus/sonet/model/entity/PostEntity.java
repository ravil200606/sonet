package ars.otus.sonet.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostEntity {
    private Integer id;
    private String text;
    private Integer authorId;
    private String authorUserId;
}
