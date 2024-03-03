package ars.otus.sonet.controller;

import ars.otus.sonet.model.dto.AuthenticationRequest;
import ars.otus.sonet.model.dto.AuthenticationToken;
import ars.otus.sonet.model.dto.ErrorResponse;
import ars.otus.sonet.model.dto.RegisterRequest;
import ars.otus.sonet.model.dto.RegisterResponse;
import ars.otus.sonet.model.dto.UserProfile;
import ars.otus.sonet.service.ProfileService;
import ars.otus.sonet.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Ошибка сервера.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "400", description = "Невалидные данные.",
                content = @Content(schema = @Schema(implementation = String.class)))
})
@RequiredArgsConstructor
public class Controller {

    private final SecurityService securityService;
    private final ProfileService profileService;

    /**
     * Авторизация.
     *
     * @param request Запрос авторизации {@link AuthenticationRequest}.
     * @return
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная аутентификация.",
                    content = @Content(schema = @Schema(implementation = AuthenticationToken.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @Operation(summary = """
            Упрощенный процесс аутентификации путем передачи идентификатор пользователя
            и получения токена для дальнейшего прохождения авторизации.
            """)
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationToken> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(securityService.authenticate(request));
    }

    /**
     * Регистрация.
     *
     * @param request Запрос регистрации {@link RegisterRequest}.
     * @return
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная регистрация.",
                    content = @Content(schema = @Schema(implementation = RegisterResponse.class)))
    })
    @Operation(summary = "Регистрация нового пользователя.")
    @PostMapping(value = "/user/register", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(profileService.register(request));
    }

    /**
     * Получение анкеты пользователя.
     *
     * @param id Запрос анкеты пользователя параметром.
     * @return
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение анкеты пользователя.",
                    content = @Content(schema = @Schema(implementation = UserProfile.class))),
            @ApiResponse(responseCode = "404", description = "Анкета не найдена.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @Operation(summary = "Получение анкеты пользователя.")
    @GetMapping(value = "/user/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> getUserProfile(
            @Parameter(description = "Идентификатор пользователя")
            @PathVariable String id) {
        return ResponseEntity.ok(profileService.getProfileById(id));
    }
}
