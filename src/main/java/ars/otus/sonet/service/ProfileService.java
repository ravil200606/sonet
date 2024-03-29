package ars.otus.sonet.service;

import ars.otus.sonet.exception.SonetException;
import ars.otus.sonet.model.dto.RegisterRequest;
import ars.otus.sonet.model.dto.RegisterResponse;
import ars.otus.sonet.model.dto.UserProfile;

import java.util.List;

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

    /**
     * Регистрация нового пользователя.
     *
     * @param request запрос на регистрацию.
     * @return идентификатор созданного пользователя.
     * @throws SonetException в случае ошибки.
     */
    RegisterResponse register(RegisterRequest request) throws SonetException;

    /**
     * Поиск списка пользователей по имени и фамилии.
     *
     * @param firstName  имя (или часть).
     * @param secondName фамилия (или часть).
     * @return список профилей найденых по запросу.
     */
    List<UserProfile> search(String firstName, String secondName);
}
