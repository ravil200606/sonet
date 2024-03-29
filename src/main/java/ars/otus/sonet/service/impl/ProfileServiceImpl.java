package ars.otus.sonet.service.impl;

import ars.otus.sonet.exception.SonetException;
import ars.otus.sonet.model.dto.RegisterRequest;
import ars.otus.sonet.model.dto.RegisterResponse;
import ars.otus.sonet.model.dto.UserProfile;
import ars.otus.sonet.repository.ProfileRepository;
import ars.otus.sonet.repository.UserRepository;
import ars.otus.sonet.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ars.otus.sonet.model.enums.ErrorCodes.DEFAULT_ERROR_CODE;
import static ars.otus.sonet.model.enums.ErrorCodes.NOT_FOUND;
import static ars.otus.sonet.util.SecurityUtils.encodePassword;
import static ars.otus.sonet.util.SecurityUtils.generateUserId;
import static java.util.Optional.ofNullable;

/**
 * Класс реализует методы управления профилем.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository repository;
    private final UserRepository userRepository;

    /**
     * Поиск анкеты пользователя по идентификатору.
     *
     * @param id идентификатор пользователя.
     * @return анкета {@link UserProfile}.
     * @throws SonetException в случае ошибки.
     */
    @Override
    public UserProfile getProfileById(String id) throws SonetException {
        var profile = repository.getProfileByUserId(id)
                .orElseThrow(() -> new SonetException("Анкета не найдена", NOT_FOUND));

        return UserProfile
                .builder()
                .id(profile.getUserId())
                .firstName(profile.getFirstName())
                .secondName(profile.getSecondName())
                .birthdate(profile.getBirthDate())
                .biography(profile.getBiography())
                .city(profile.getCity())
                .build();
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param request запрос на регистрацию.
     * @return идентификатор созданного пользователя.
     * @throws SonetException в случае ошибки.
     */
    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) throws SonetException {
        var userId = generateUserId();
        var newSonetUserKey =
                ofNullable(userRepository.createUser(userId, encodePassword(request.getPassword()))).
                        orElseThrow(() -> new SonetException("Ошибка при создании записи SONET_USER. Значение ключа пустое.",
                                DEFAULT_ERROR_CODE));
        var newProfileId = ofNullable(repository.createProfile(ars.otus.sonet.model.entity.UserProfile
                .builder()
                .id(newSonetUserKey)
                .firstName(request.getFirstName())
                .secondName(request.getSecondName())
                .birthDate(request.getBirthdate())
                .biography(request.getBiography())
                .city(request.getCity())
                .build())
        ).orElseThrow(() -> new SonetException("Ошибка при создании записи USER_PROFILE. Значение ключа пустое.",
                DEFAULT_ERROR_CODE));
        log.debug("Новая запись в USER_PROFILE c Id {}.", newProfileId);
        return new RegisterResponse(userId);
    }

    /**
     * Поиск списка пользователей по имени и фамилии.
     *
     * @param firstName  имя (или часть).
     * @param secondName фамилия (или часть).
     * @return список профилей найденых по запросу.
     */
    @Override
    public List<UserProfile> search(String firstName, String secondName) {
        return repository.searchByNameAndSurname(firstName, secondName)
                .stream().map(
                        value -> UserProfile
                                .builder()
                                .id(value.getUserId())
                                .firstName(value.getFirstName())
                                .secondName(value.getSecondName())
                                .birthdate(value.getBirthDate())
                                .biography(value.getBiography())
                                .city(value.getCity())
                                .build())
                .toList();
    }
}
