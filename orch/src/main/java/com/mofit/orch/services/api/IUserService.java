package com.mofit.orch.services.api;

import com.mofit.mainmofitapiservice.models.LoginUserRequest;
import com.mofit.mainmofitapiservice.models.SignUserResponse;
import com.mofit.mainmofitapiservice.models.SignupUserRequest;
import com.mofit.mainmofitapiservice.models.User;

import java.io.IOException;

public interface IUserService {

    User getUserByEmail(String username);
    User getUserByUserId(Integer userId);
    SignUserResponse loginUser(LoginUserRequest loginUserRequest);
    SignUserResponse createNewUser(SignupUserRequest userRequest) throws IOException;
    void updateUserPassword(Integer userId, String oldPassword, String newPassword);
}
