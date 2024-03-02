package ars.otus.sonet.repository;

import ars.otus.sonet.model.entity.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
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
}
