package com.mofit.orch.endpoints;

import com.mofit.mainmofitapiservice.models.LoginUserRequest;
import com.mofit.mainmofitapiservice.models.SignUserResponse;
import com.mofit.mainmofitapiservice.models.SignupUserRequest;
import com.mofit.orch.services.api.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/users")
//@PreAuthorize("hasAuthority('CITY_MODULE: READ')")
public class UserController {

    private IUserService userService;

    @Autowired
    public UserController(final IUserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Creates new User")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public SignUserResponse createNewUser(@RequestBody @Valid SignupUserRequest request)
        throws IOException {
        return userService.createNewUser(request);
    }

    @ApiOperation(value = "Login User")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public SignUserResponse loginUser(@RequestBody @Valid LoginUserRequest loginUserRequest) {
        return userService.loginUser(loginUserRequest);
    }

    @ApiOperation(value = "Update user password")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/password/{userId}")
    public void updateUserPassword(@PathVariable Integer userId,
                                   @RequestParam String oldPassword,
                                   @RequestParam String newPassword) {
        userService.updateUserPassword(userId, oldPassword, newPassword);
    }

}