package ars.otus.sonet.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Schema(name = "first_name", example = "Александр", description = "Имя пользователя.")
    @JsonProperty(value = "first_name", required = true)
    private String firstName;

    @Schema(name = "second_name", example = "Иванов", description = "Фамилия.")
    @JsonProperty(value = "second_name")
    private String secondName;

    @Schema(name = "birthdate", type = "String", example = "2017-02-01", description = "Дата рождения.")
    @JsonProperty(value = "birthdate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @Schema(name = "biography", example = "Бег, чтение.", description = "Хобби, интересы и т.п.")
    @JsonProperty(value = "biography")
    private String biography;

    @Schema(name = "city", example = "Москва.", description = "Город проживания.")
    @JsonProperty(value = "city")
    private String city;

    @Schema(name = "password", example = "<USER_PASSWORD>.", description = "Секретная строка.")
    @JsonProperty(value = "password", required = true)
    private String password;
}