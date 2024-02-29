package ars.otus.sonet.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class ErrorResponse {
    @Schema(name = "message", example = "Недопустимое значение даты.", description = "Описание ошибки.")
    private String message;

    @Schema(name = "request_id", example = "1d535fd6-7521-4cb1-aa6d-031be7123c4d.",
            description = "Идентификатор запроса. Предназначен для более быстрого поиска проблем.")
    @JsonProperty(value = "request_id")
    private String requestId;

    @Schema(name = "code", example = "101",
            description = "Код ошибки. Предназначен для классификации проблем и более быстрого решения проблем.")
    private Integer code;
}
