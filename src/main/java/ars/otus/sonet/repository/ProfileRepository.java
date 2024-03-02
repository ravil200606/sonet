package ars.otus.sonet.repository;

import ars.otus.sonet.model.entity.UserProfile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий содержит методы для работы с профилем пользователя.
 */
@Repository
public class ProfileRepository extends BaseRepository {
    public ProfileRepository(JdbcTemplate serviceJdbcTemplate) {
        super(serviceJdbcTemplate);
    }

    private static final String SQL_FIND_PROFILE_BY_USER_ID = """
            SELECT UP.ID, UP.FIRST_NAME, UP.SECOND_NAME, UP.BIRTH_DATE, UP.BIOGRAPHY, UP.CITY
            FROM USER_PROFILE UP
            JOIN SONET_USER SU ON SU.ID = UP.SONET_USER_ID
            WHERE SU.USER_ID = :userId
            """;

    /**
     * Поиск профиля пользователя по идентификатору.
     *
     * @param userId идентификатор пользователя.
     * @return профиль, если найден {@link Optional<UserProfile>}.
     */
    public Optional<UserProfile> getProfileByUserId(String userId) {
        return getNamedParameterJdbcTemplate().query(
                SQL_FIND_PROFILE_BY_USER_ID,
                Map.of("userId", userId),
                rs -> rs.next() ? Optional.of(
                        UserProfile.builder()
                                .id(rs.getInt("ID"))
                                .userId(userId)
                                .firstName(rs.getString("FIRST_NAME"))
                                .secondName(rs.getString("SECOND_NAME"))
                                .biography(rs.getString("BIOGRAPHY"))
                                .birthDate(rs.getDate("BIRTH_DATE").toLocalDate())
                                .city(rs.getString("CITY"))
                                .build())
                        : Optional.empty()
        );
    }
}
