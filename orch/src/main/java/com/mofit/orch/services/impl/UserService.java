package com.mofit.orch.services.impl;

import com.mofit.orch.dao.IUserDAO;
import com.mofit.orch.exceptions.CustomClientException;
import com.mofit.orch.exceptions.RestTemplateErrorHandler;
import com.mofit.orch.security.JwtTokenProvider;
import com.mofit.orch.services.api.IUserService;
import com.mofit.user.models.LoginUserRequest;
import com.mofit.user.models.SignUserResponse;
import com.mofit.user.models.SignupUserRequest;
import com.mofit.user.models.UpdateUserPasswordRequest;
import com.mofit.user.models.User;
import com.mofit.user.models.UserPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final IUserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private RestOperations restTemplate;

    @Value("${services.user.loginUser}")
    String loginUserUrl;

    @Value("${services.user.signupUser}")
    String signupUserUrl;

    @Value("${services.user.getUserById}")
    String getUserByUserIdUrl;

    @Value("${services.user.updateUserPassword}")
    String updateUserPasswordUrl;

    @Autowired
    public UserService(IUserDAO userDAO, PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider,
                       AuthenticationManager authenticationManager, RestTemplateBuilder restTemplateBuilder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;

        restTemplate = restTemplateBuilder.errorHandler(new RestTemplateErrorHandler()).build();
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email)
            .orElseThrow(() -> new CustomClientException(
                "There is no existing account with that email: " + email, HttpStatus.NOT_FOUND));
    }

    @Override
    public User getUserByUserId(Integer userId) {
        ParameterizedTypeReference<User> responseType = new ParameterizedTypeReference<User>() {};

        ResponseEntity<User> responseEntity =
            restTemplate.exchange(getUserByUserIdUrl,
                                  HttpMethod.GET,
                                  null,
                                  responseType, userId);

        return responseEntity.getBody();
    }

    @Override
    public SignUserResponse loginUser(LoginUserRequest loginUserRequest) {
        String email = loginUserRequest.getEmail();
        String password = loginUserRequest.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        User loggedUser = userDAO.getUserByEmail(email).get();

        return SignUserResponse.builder()
            .userId(loggedUser.getUserId())
            .token(jwtTokenProvider.createToken(
                email, loggedUser.getUserPermissions(), loggedUser.getUserTypeIds(), loggedUser.getUserId()))
            .userTypeIds(loggedUser.getUserTypeIds())
            .build();
    }

    @Transactional
    @Override
    public SignUserResponse createNewUser(SignupUserRequest userRequest) {
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        ParameterizedTypeReference<SignUserResponse> responseType =
            new ParameterizedTypeReference<SignUserResponse>() {
            };

            ResponseEntity<SignUserResponse> responseEntity =
                restTemplate.exchange(signupUserUrl,
                                      HttpMethod.POST,
                                      new HttpEntity<>(userRequest),
                                      responseType);

            Integer userId = responseEntity.getBody().getUserId();

            List<UserPermission> userPermissions = getUserByUserId(userId).getUserPermissions();

            responseEntity.getBody().setToken(jwtTokenProvider.createToken(
                userRequest.getEmail(), userPermissions, Collections.emptyList(), userId));

            return responseEntity.getBody();
    }

    @Override
    public void updateUserPassword(Integer userId, UpdateUserPasswordRequest updateRequest) {
        String userEmail = getUserByUserId(userId).getEmail();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, updateRequest.getOldPassword()));

        String newEncodedPassword = passwordEncoder.encode(updateRequest.getNewPassword());

        UriComponents UriBuilder = UriComponentsBuilder
            .fromUriString(updateUserPasswordUrl)
            .queryParam("newPassword", newEncodedPassword).build();

            restTemplate.exchange(UriBuilder.toUriString(),
                                  HttpMethod.PUT,
                                  null,
                                  Object.class, userId);
    }

}
