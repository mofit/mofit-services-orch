package com.mofit.orch.dao.util;

import com.mofit.user.models.AccessModule;
import com.mofit.user.models.Client;
import com.mofit.user.models.Permission;
import com.mofit.user.models.User;
import com.mofit.user.models.UserPermission;
import com.mofit.user.models.UserType;
import com.mofit.user.models.UserTypeId;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setUserId(rs.getInt("userId"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        List<UserPermission> userPermissions = new ArrayList<>();
        List<UserTypeId> userTypeIds = new ArrayList<>();

        // Get it as objects, because rs.getInt() will return 0 in case of null column
        Integer clientId = (Integer) rs.getObject("clientId");
        Integer trainerId = (Integer) rs.getObject("trainerId");

        if(clientId != null) {
            userTypeIds.add(
                UserTypeId.builder().userType(UserType.CLIENT).userTypeId(clientId).build());
        }

        if(trainerId != null) {
            userTypeIds.add(
                UserTypeId.builder().userType(UserType.TRAINER).userTypeId(trainerId).build());
        }

        do {
            userPermissions.add(UserPermission.builder()
                .accessModule(AccessModule.builder()
                          .moduleId(rs.getInt("moduleId"))
                          .moduleName(rs.getString("name"))
                          .build())
                .permission(Permission.valueOf(rs.getString("permission")))
                .userPermissionId(rs.getInt("userPermissionId"))
                .build());
        } while (rs.next());

        user.setUserTypeIds(userTypeIds);
        user.setUserPermissions(userPermissions.stream().distinct().collect(Collectors.toList()));
        return user;
    }
}