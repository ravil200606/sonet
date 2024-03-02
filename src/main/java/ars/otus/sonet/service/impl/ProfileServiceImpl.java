package ars.otus.sonet.service.impl;

import ars.otus.sonet.exception.SonetException;
import ars.otus.sonet.model.dto.UserProfile;
import ars.otus.sonet.repository.ProfileRepository;
import ars.otus.sonet.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static ars.otus.sonet.model.enums.ErrorCodes.NOT_FOUND;

/**
 * Класс реализует методы управления профилем.
 */
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository repository;

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
}
