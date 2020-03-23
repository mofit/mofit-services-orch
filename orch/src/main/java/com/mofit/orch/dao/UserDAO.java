package com.mofit.orch.dao;
import com.mofit.orch.dao.util.SqlQueries;
import com.mofit.orch.dao.util.UserRowMapper;
import com.mofit.user.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDAO implements IUserDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserDAO(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("email", email);
        try {
            return Optional.of(namedParameterJdbcTemplate.queryForObject(
                SqlQueries.GET_USER_BY_EMAIL, sqlParameterSource, new UserRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

}
