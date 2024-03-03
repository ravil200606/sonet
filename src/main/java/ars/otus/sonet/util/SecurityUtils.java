package ars.otus.sonet.util;

import lombok.experimental.UtilityClass;
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
     * @return случайный идентификатор.
     */
    public static String generateUserId() {
        return UUID.randomUUID().toString();
    }
}
