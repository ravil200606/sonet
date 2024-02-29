package ars.otus.sonet.service.impl;

import ars.otus.sonet.exception.SonetException;
import ars.otus.sonet.model.dto.UserProfile;
import ars.otus.sonet.service.ProfileService;
import org.springframework.stereotype.Service;

/**
 * Класс реализует методы управления профилем.
 */
@Service
public class ProfileServiceImpl implements ProfileService {
    /**
     * Поиск анкеты пользователя по идентификатору.
     *
     * @param id идентификатор пользователя.
     * @return анкета {@link UserProfile}.
     * @throws SonetException в случае ошибки.
     */
    @Override
    public UserProfile getProfileById(String id) throws SonetException {
        return UserProfile.builder().build();
    }
}
