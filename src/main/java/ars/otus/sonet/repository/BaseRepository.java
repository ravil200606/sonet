package ars.otus.sonet.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Repository
public class BaseRepository {

    private final JdbcTemplate serviceJdbcTemplate;

    protected JdbcTemplate getJdbcTemplate() {
        return serviceJdbcTemplate;
    }

    protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(requireNonNull(getJdbcTemplate().getDataSource()));
    }
}
