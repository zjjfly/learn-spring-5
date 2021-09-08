package com.github.zjjfly.spring5.repo.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.zjjfly.spring5.domain.User;
import com.github.zjjfly.spring5.repo.UserRepo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author zjjfly[https://github.com/zjjfly]
 * @date 2021/2/19
 */
@Repository
public class UserRepoImpl implements UserRepo {

    private SimpleJdbcInsert jdbcInsert;

    private ObjectMapper objectMapper;

    public UserRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user")
                .usingGeneratedKeyColumns("id");
        this.objectMapper = new ObjectMapper()
                .configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
    }

    @Override
    public long save(User user) {
        return jdbcInsert.executeAndReturnKey(objectMapper.convertValue(user, Map.class)).longValue();
    }

}
