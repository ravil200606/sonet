package ars.otus.sonet.service;

import ars.otus.sonet.exception.SonetException;
import ars.otus.sonet.model.dto.AuthenticationRequest;
import ars.otus.sonet.model.dto.AuthenticationToken;
import ars.otus.sonet.model.dto.RegisterRequest;
import ars.otus.sonet.model.dto.RegisterResponse;

/**
 * Сервис описывает методы аутентификации.
 */
public interface SecurityService {
    /**
     * Аутентификация пользователя.
     *
     * @param request запрос на авторизацию.
     * @return токен в случае успешной аутентификации.
     * @throws SonetException в случае ошибки аутентификации.
     */
    AuthenticationToken authenticate(AuthenticationRequest request) throws SonetException;

    /**
     * Регистрация нового пользователя.
     *
     * @param request запрос на регистрацию.
     * @return Сообщение об успешной регистрации.
     * @throws SonetException в случае ошибки при регистрации.
     */
    RegisterResponse register(RegisterRequest request) throws SonetException;;
}
