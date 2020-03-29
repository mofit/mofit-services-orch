package com.mofit.orch.services.api;

import com.mofit.user.models.AccessModule;
import com.mofit.user.models.Permission;
import com.mofit.user.models.UserPermission;

import java.util.List;

public interface IPermissionService {
    List<AccessModule> getAccessModules();
    String insertAccessModules(List<AccessModule> modules);
    void insertUserPermissions(Integer userId, List<UserPermission> permissions);
    List<UserPermission> getUserPermissionsByUserId(Integer userId);
}
