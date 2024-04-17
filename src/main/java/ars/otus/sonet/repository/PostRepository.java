package ars.otus.sonet.repository;

import ars.otus.sonet.model.entity.PostEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class PostRepository extends BaseRepository {

    public PostRepository(JdbcTemplate serviceJdbcTemplate) {
        super(serviceJdbcTemplate);
    }

    private static final String SQL_FEED = """
            SELECT P.id AS ID, P.content AS CONTENT, SU.user_id AS AUTHOR FROM POSTS P
            JOIN USER_FRIENDS UF ON UF.friend_id = P.user_id
            JOIN SONET_USER SU ON SU.id = UF.friend_id
            WHERE UF.user_id = :userId
            ORDER BY P.id DESC LIMIT :limit OFFSET :offset
            """;

    /**
     * Возвращает список записей друзей пользователя таблицы POSTS.
     *
     * @param userId пользователь по друзьям которого делать выборку.
     * @param offset смещение с которого начинать выдачу записей.
     * @param limit  лимит выдачи записей.
     * @return список записей {@link List<PostEntity>}.
     */
    public List<PostEntity> getFriendFeed(Integer userId, Integer offset, Integer limit) {
        log.info("Запрос постов из БД. Для друзей пользователя {} смещение {} лимит {}.", userId, offset, limit);
        return getNamedParameterJdbcTemplate().query(SQL_FEED,
                new MapSqlParameterSource("userId", userId)
                        .addValue("limit", limit)
                        .addValue("offset", offset),
                (rs, rowNum) -> PostEntity.builder()
                        .id(rs.getInt("ID"))
                        .text(rs.getString("CONTENT"))
                        .authorUserId(rs.getString("AUTHOR"))
                        .build());

    }
}
