package ars.otus.sonet.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO токен при успешной аутентификации.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationToken {
    @Schema(name = "token", example = "e4d2e6b0-cde2-42c5-aac3-0b8316f21e58", description = "Токен аутентификации.")
    private String token;
}
