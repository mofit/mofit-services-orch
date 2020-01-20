package com.mofit.orch.endpoints;

import com.mofit.mainmofitapiservice.models.AccessModule;
import com.mofit.mainmofitapiservice.models.Permission;
import com.mofit.orch.services.api.IPermissionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/access")
public class PermissionsController {

    private IPermissionService permissionService;

    @Autowired
    public PermissionsController(final IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @ApiOperation(value = "Get user access modules")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/modules")
    public List<AccessModule> getAccessModules() {
        return permissionService.getAccessModules();
    }

    @ApiOperation(value = "Insert access modules")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/modules")
    public String insertAccessModules(@RequestBody List<AccessModule> modules) {
        return permissionService.insertAccessModules(modules) + " Access Modules were inserted";
    }

    @ApiOperation(value = "Insert User permissions")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/access/{userId}/permissions")
    public void loginUser(@PathVariable Integer userId, @RequestBody @Valid List<Permission> permissions) {
        permissionService.insertUserPermissions(userId, permissions);
    }
}
