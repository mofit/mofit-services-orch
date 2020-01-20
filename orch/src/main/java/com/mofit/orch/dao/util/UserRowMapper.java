package com.mofit.orch.dao.util;

import com.mofit.mainmofitapiservice.models.Client;
import com.mofit.mainmofitapiservice.models.Permission;
import com.mofit.mainmofitapiservice.models.User;
import com.mofit.mainmofitapiservice.models.UserType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new Client();

        user.setUserId(rs.getInt("user_id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setUserType(UserType.valueOf(rs.getString("user_type")));
        List<Permission> userPermissions = new ArrayList<>();
        do {
            userPermissions.add(Permission.builder()
                .moduleId(rs.getInt("module_id"))
                .permission(rs.getString("permission"))
                .module(rs.getString("name"))
                .build());
        } while (rs.next());

        user.setPermissions(userPermissions);
        return user;
    }
}