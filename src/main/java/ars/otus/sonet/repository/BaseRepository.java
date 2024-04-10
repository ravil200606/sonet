package ars.otus.sonet.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Repository
public class BaseRepository {

    @Autowired
    @Qualifier("masterJdbcTemplate")
    private JdbcTemplate masterServiceJdbcTemplate;

    @Autowired
    @Qualifier("slaveJdbcTemplate")
    private JdbcTemplate slaveServiceJdbcTemplate;


    protected JdbcTemplate getMasterJdbcTemplate() {
        return masterServiceJdbcTemplate;
    }

    protected JdbcTemplate getSlaveJdbcTemplate() {
        return slaveServiceJdbcTemplate;
    }

    protected NamedParameterJdbcTemplate getMasterNamedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(requireNonNull(getMasterJdbcTemplate().getDataSource()));
    }

    protected NamedParameterJdbcTemplate getSlaveNamedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(requireNonNull(getSlaveJdbcTemplate().getDataSource()));
    }
}
