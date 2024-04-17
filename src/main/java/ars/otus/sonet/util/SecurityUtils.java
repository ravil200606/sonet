package ars.otus.sonet.util;

import ars.otus.sonet.model.entity.UserEntity;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@UtilityClass
public class SecurityUtils {
    private static final PasswordEncoder passwordEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();

    /**
     * Шифрует строку пароля алгоритмом BCRYPT.
     *
     * @param password строка пароля.
     * @return результат хэширования.
     */
    public static String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * Сравнение пароля пользователя и зашифрованного значения.
     *
     * @param password          пароль пользователя из запроса.
     * @param encryptedPassword зашифрованное значение.
     * @return true если совпадает.
     */
    public static boolean matches(String password, String encryptedPassword) {
        return passwordEncoder.matches(password, encryptedPassword);
    }

    /**
     * Генерация идентификатора пользователя.
     *
     * @return случайный идентификатор.
     */
    public static String generateUserId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Возвращает Id аутентифицированного пользователя.
     *
     * @return Id пользователя.
     */
    public static Integer getAuthUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return user.getId();
    }

    /**
     * Возвращает логин аутентифицированного пользователя.
     *
     * @return логин пользователя.
     */
    public static String getAuthUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return user.getUserId();
    }
}
