package ars.otus.sonet.service.impl;

import ars.otus.sonet.exception.SonetException;
import ars.otus.sonet.model.dto.AuthenticationRequest;
import ars.otus.sonet.model.dto.AuthenticationToken;
import ars.otus.sonet.repository.UserRepository;
import ars.otus.sonet.service.SecurityService;
import ars.otus.sonet.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import static ars.otus.sonet.configuration.filter.JwtAuthenticationFilter.BEARER_TOKEN_PREFIX;
import static ars.otus.sonet.model.enums.ErrorCodes.BAD_CREDENTIALS;
import static ars.otus.sonet.model.enums.ErrorCodes.NOT_FOUND;

/**
 * Реализация методов аутентификации.
 */
@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final SonetJwtService jwtService;
    /**
     * Аутентификация пользователя.
     *
     * @param request запрос на авторизацию.
     * @return токен в случае успешной аутентификации.
     * @throws SonetException в случае ошибки аутентификации.
     */
    @Override
    public AuthenticationToken authenticate(AuthenticationRequest request) throws SonetException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getId(),
                request.getPassword()));
        var user = userRepository.getUserByUserId(request.getId())
                .orElseThrow(() -> new SonetException("Пользователь не найден", NOT_FOUND));
        if (SecurityUtils.matches(request.getPassword(), user.getPassword())) {
            var jwt = jwtService.generateToken(user);
            return new AuthenticationToken(BEARER_TOKEN_PREFIX+jwt);
        } else {
            throw new SonetException("Не верный пароль.", BAD_CREDENTIALS);
        }
    }
}
