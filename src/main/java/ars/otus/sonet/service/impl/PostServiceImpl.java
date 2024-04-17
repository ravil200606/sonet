package ars.otus.sonet.service.impl;

import ars.otus.sonet.model.dto.Post;
import ars.otus.sonet.repository.PostRepository;
import ars.otus.sonet.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Класс реализует методы работы с публикациями.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository repository;

    /**
     * Возвращает последние посты друзей.
     *
     * @param userId технический ключ пользователя.
     * @param offset смещение с которого начинать выдачу.
     * @param limit  количество возвращаемых за один запрос значений.
     * @return список постов {@link List<Post>}.
     */
    @Override
    @Cacheable("posts")
    public List<Post> getFreshFriendPosts(Integer userId, Integer offset, Integer limit) {
        return repository.getFriendFeed(userId, offset, limit)
                .stream()
                .map(post -> new Post(post.getId(),
                        post.getText(),
                        post.getAuthorUserId()))
                .toList();
    }
}
