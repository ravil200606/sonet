package ars.otus.sonet.service;

import ars.otus.sonet.exception.SonetException;
import ars.otus.sonet.model.dto.UserProfile;

/**
 * Интерфейс описывает методы управления профилем.
 */
public interface ProfileService {
    /**
     * Поиск анкеты пользователя по идентификатору.
     *
     * @param id идентификатор пользователя.
     * @return анкета {@link UserProfile}.
     * @throws SonetException в случае ошибки.
     */
    UserProfile getProfileById(String id) throws SonetException;
}
