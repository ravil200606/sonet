package ars.otus.sonet.repository;

import ars.otus.sonet.model.entity.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий содержит методы для работы с данными пользователя.
 */
@Repository
public class UserRepository extends BaseRepository {
    public UserRepository(JdbcTemplate serviceJdbcTemplate) {
        super(serviceJdbcTemplate);
    }

    private static final String SQL_FIND_USER_BY_USER_ID = """
            SELECT ID, USER_ID, PASSWORD FROM SONET_USER WHERE USER_ID = :userId 
            """;

    /**
     * Поиск пользователя по идентификатору.
     *
     * @param userId идентификатор пользователя.
     * @return пользователь, если найден {@link Optional<UserEntity>}.
     */
    public Optional<UserEntity> getUserByUserId(String userId) {
        return getNamedParameterJdbcTemplate().query(
                SQL_FIND_USER_BY_USER_ID,
                Map.of("userId", userId),
                rs -> rs.next() ? Optional.of(
                        UserEntity
                                .builder()
                                .id(rs.getInt("ID"))
                                .userId(rs.getString("USER_ID"))
                                .password(rs.getString("PASSWORD"))
                                .build()) : Optional.empty()
        );
    }

    private static final String SQL_INSERT = "INSERT INTO SONET_USER(USER_ID, PASSWORD) VALUES (:userId, :password)";

    /**
     * Создание новой записи в таблице SONET_USER.
     *
     * @param userId   идентификатор пользователя.
     * @param password пароль.
     * @return ключ новой записи.
     */
    public Integer createUser(String userId, String password) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getNamedParameterJdbcTemplate().update(
                SQL_INSERT,
                new MapSqlParameterSource("userId", userId)
                        .addValue("password", password),
                keyHolder,
                new String[] { "id" }
        );

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            return null;
        }
    }
}
