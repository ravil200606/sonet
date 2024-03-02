package ars.otus.sonet.service.impl;

import ars.otus.sonet.exception.SonetException;
import ars.otus.sonet.model.dto.AuthenticationRequest;
import ars.otus.sonet.model.dto.AuthenticationToken;
import ars.otus.sonet.model.dto.RegisterRequest;
import ars.otus.sonet.model.dto.RegisterResponse;
import ars.otus.sonet.repository.UserRepository;
import ars.otus.sonet.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static ars.otus.sonet.model.enums.ErrorCodes.BAD_CREDENTIALS;
import static ars.otus.sonet.model.enums.ErrorCodes.NOT_FOUND;

/**
 * Реализация методов аутентификации.
 */
@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    private static final PasswordEncoder passwordEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private final UserRepository userRepository;

    /**
     * Аутентификация пользователя.
     *
     * @param request запрос на авторизацию.
     * @return токен в случае успешной аутентификации.
     * @throws SonetException в случае ошибки аутентификации.
     */
    @Override
    public AuthenticationToken authenticate(AuthenticationRequest request) throws SonetException {
        var user = userRepository.getUserByUserId(request.getId())
                .orElseThrow(() -> new SonetException("Пользователь не найден", NOT_FOUND));
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthenticationToken("TOKEN");
        } else {
            throw new SonetException("Не верный пароль.", BAD_CREDENTIALS);
        }
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

    /**
     * Шифрует строку пароля алгоритмом BCRYPT.
     *
     * @param password строка пароля.
     * @return результат хэширования.
     */
    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
