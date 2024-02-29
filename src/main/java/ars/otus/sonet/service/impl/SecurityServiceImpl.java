package ars.otus.sonet.service.impl;

import ars.otus.sonet.exception.SonetException;
import ars.otus.sonet.model.dto.AuthenticationRequest;
import ars.otus.sonet.model.dto.AuthenticationToken;
import ars.otus.sonet.model.dto.RegisterRequest;
import ars.otus.sonet.model.dto.RegisterResponse;
import ars.otus.sonet.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Реализация методов аутентификации.
 */
@Service
public class SecurityServiceImpl implements SecurityService {
    /**
     * Аутентификация пользователя.
     *
     * @param request запрос на авторизацию.
     * @return токен в случае успешной аутентификации.
     * @throws SonetException в случае ошибки аутентификации.
     */
    @Override
    public AuthenticationToken authenticate(AuthenticationRequest request) throws SonetException {
        return new AuthenticationToken(UUID.randomUUID().toString());
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param request запрос на регистрацию.
     * @return Сообщение об успешной регистрации.
     * @throws SonetException в случае ошибки при регистрации.
     */
    @Override
    public RegisterResponse register(RegisterRequest request) throws SonetException {
        return new RegisterResponse(UUID.randomUUID().toString());
    }
}
