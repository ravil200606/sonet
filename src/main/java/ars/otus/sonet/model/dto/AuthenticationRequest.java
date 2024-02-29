package ars.otus.sonet.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO запрос аутентификации.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @Schema(name = "id", example = "USER_ID", description = "Идентификатор пользователя.")
    private String id;
    @Schema(name = "password", example = "USER_PASSWORD", description = "Пароль.")
    private String password;
}
