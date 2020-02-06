package com.mofit.orch.services.impl;


import com.mofit.mainmofitapiservice.models.CustomClientException;
import com.mofit.mainmofitapiservice.models.LoginUserRequest;
import com.mofit.mainmofitapiservice.models.SignUserResponse;
import com.mofit.mainmofitapiservice.models.SignupUserRequest;
import com.mofit.mainmofitapiservice.models.User;
import com.mofit.orch.dao.IUserDAO;
import com.mofit.orch.exceptions.RestTemplateErrorHandler;
import com.mofit.orch.security.JwtTokenProvider;
import com.mofit.orch.services.api.IUserService;
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

import static com.mofit.mainmofitapiservice.models.UserType.PLAIN_USER;

@Service
public class UserService implements IUserService {

    private final IUserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private RestOperations restTemplate;
    private final RestTemplateBuilder restTemplateBuilder;

    @Value("${services.user.loginUser}")
    String loginUserUrl;

    @Value("${services.user.signupUser}")
    String signupUserUrl;

    @Autowired
    public UserService(IUserDAO userDAO, PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider,
                       AuthenticationManager authenticationManager, RestTemplateBuilder restTemplateBuilder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.restTemplateBuilder = restTemplateBuilder;

        restTemplate = restTemplateBuilder.errorHandler(new RestTemplateErrorHandler()).build();
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email)
            .orElseThrow(() -> new CustomClientException(
                "There is no existing account with that email: " + email, HttpStatus.NOT_FOUND));
    }

    @Override
    public SignUserResponse loginUser(LoginUserRequest loginUserRequest) {
        String email = loginUserRequest.getEmail();
        String password = loginUserRequest.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        User loggedUser = userDAO.getUserByEmail(email).get();

        return SignUserResponse.builder()
            .userId(loggedUser.getUserId())
            .userType(loggedUser.getUserType())
            .token(jwtTokenProvider.createToken(
                email, loggedUser.getPermissions(), loggedUser.getUserType().name(), loggedUser.getUserId()))
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
            responseEntity.getBody().setToken(jwtTokenProvider.createToken(
                userRequest.getEmail(), userRequest.getPermissions(), PLAIN_USER.name(), userId));

            return responseEntity.getBody();
    }
}
