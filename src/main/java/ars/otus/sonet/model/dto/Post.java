package ars.otus.sonet.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @JsonProperty(value = "id", required = true)
    private Integer id;
    @JsonProperty(value = "text", required = true)
    private String text;
    @JsonProperty(value = "author_user_id", required = true)
    private String authorUserId;
}
