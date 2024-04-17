package ars.otus.sonet.service;

import ars.otus.sonet.model.dto.Post;

import java.util.List;

/**
 * Интерфейс описывает методы работы с публикациями.
 */
public interface PostService {
    /**
     * Возвращает последние посты друзей.
     *
     * @param userId технический ключ пользователя.
     * @param offset смещение с которого начинать выдачу.
     * @param limit  количество возвращаемых за один запрос значений.
     * @return список постов {@link List<Post>}.
     */
    List<Post> getFreshFriendPosts(Integer userId, Integer offset, Integer limit);
}
