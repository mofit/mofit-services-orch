package com.mofit.orch.services.api;

import com.mofit.user.models.LoginUserRequest;
import com.mofit.user.models.SignUserResponse;
import com.mofit.user.models.SignupUserRequest;
import com.mofit.user.models.UpdateUserPasswordRequest;
import com.mofit.user.models.User;

import java.io.IOException;

public interface IUserService {

    User getUserByEmail(String username);
    User getUserByUserId(Integer userId);
    SignUserResponse loginUser(LoginUserRequest loginUserRequest);
    SignUserResponse createNewUser(SignupUserRequest userRequest) throws IOException;
    void updateUserPassword(Integer userId, UpdateUserPasswordRequest updatePasswordRequest);
}
