package ars.otus.sonet.repository;

import ars.otus.sonet.exception.SonetException;
import ars.otus.sonet.model.entity.UserProfile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    private static final String SQL_INSERT = """
            INSERT INTO USER_PROFILE(
             SONET_USER_ID,
             FIRST_NAME,
             SECOND_NAME,
             BIRTH_DATE,
             BIOGRAPHY,
             CITY
            )
            VALUES(
             :sonetUserId,
             :firstName,
             :secondName,
             :birthDate,
             :biography,
             :city
            )
            """;

    /**
     * Создание новой записи в таблице USER_PROFILE.
     *
     * @param profile модель для нового профиля.
     * @return ключ новой записи.
     */
    public Integer createProfile(UserProfile profile) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getNamedParameterJdbcTemplate().update(
                SQL_INSERT,
                new MapSqlParameterSource("sonetUserId", profile.getId())
                        .addValue("firstName", profile.getFirstName())
                        .addValue("secondName", profile.getSecondName())
                        .addValue("birthDate", profile.getBirthDate())
                        .addValue("biography", profile.getBiography())
                        .addValue("city", profile.getCity()),
                keyHolder,
                new String[]{"id"}
        );
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            throw new SonetException("Ошибка при получении ключаа новой записи USER_PROFILE");
        }
    }

    private static final String SQL_SEARCH = """
            SELECT 
            (SELECT USER_ID FROM SONET_USER WHERE ID = P.SONET_USER_ID) AS USER_ID, 
            P.FIRST_NAME, 
            P.SECOND_NAME, 
            P.BIRTH_DATE,
            P.BIOGRAPHY,
            P.CITY
            FROM USER_PROFILE P 
            WHERE P.FIRST_NAME LIKE (:firstName) AND P.SECOND_NAME LIKE (:secondName) ORDER BY P.ID
            """;

    public List<UserProfile> searchByNameAndSurname(String firstName, String secondName) {
        return getNamedParameterJdbcTemplate().query(SQL_SEARCH,
                new MapSqlParameterSource("firstName", firstName + "%")
                        .addValue("secondName", secondName + "%"),
                (rs, rowNum) -> UserProfile.builder()
                        .userId(rs.getString("USER_ID"))
                        .firstName(rs.getString("FIRST_NAME"))
                        .secondName(rs.getString("SECOND_NAME"))
                        .birthDate(rs.getDate("BIRTH_DATE").toLocalDate())
                        .biography(rs.getString("BIOGRAPHY"))
                        .city(rs.getString("CITY"))
                        .build());
    }
}
