package com.mofit.orch.services.api;

import com.mofit.mainmofitapiservice.models.LoginUserRequest;
import com.mofit.mainmofitapiservice.models.SignUserResponse;
import com.mofit.mainmofitapiservice.models.SignupUserRequest;
import com.mofit.mainmofitapiservice.models.User;

public interface IUserService {

    User getUserByEmail(String username);
    SignUserResponse loginUser(LoginUserRequest loginUserRequest);
    SignUserResponse createNewUser(SignupUserRequest userRequest);
}
