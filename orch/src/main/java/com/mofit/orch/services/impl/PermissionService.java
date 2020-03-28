package com.mofit.orch.services.impl;

import com.mofit.orch.exceptions.RestTemplateErrorHandler;
import com.mofit.orch.services.api.IPermissionService;
import com.mofit.user.models.AccessModule;
import com.mofit.user.models.Permission;
import com.mofit.user.models.UserPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionService implements IPermissionService {

    private static final String USER_ID_KEY = "userId";

    private final RestOperations restTemplate;
    private final RestTemplateBuilder restTemplateBuilder;

    @Value("${services.user.getAccessModules}")
    String getAccessModulesUrl;

    @Value("${services.user.insertAccessModules}")
    String insertAccessModulesUrl;

    @Value("${services.user.insertUserPermissions}")
    String insertUserPermissions;

    @Autowired
    public PermissionService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
        restTemplate = restTemplateBuilder.errorHandler(new RestTemplateErrorHandler()).build();
    }

    @Override
    public List<AccessModule> getAccessModules() {

        ParameterizedTypeReference<List<AccessModule>> responseType =
            new ParameterizedTypeReference<List<AccessModule>>() {
            };

        ResponseEntity<List<AccessModule>> responseEntity =
            restTemplate.exchange(getAccessModulesUrl,
                                  HttpMethod.GET,
                                  null,
                                  responseType);

        return responseEntity.getBody();
    }

    @Override
    @Transactional
    public String insertAccessModules(List<AccessModule> modules) {

        ResponseEntity<String> responseEntity =
            restTemplate.exchange(insertAccessModulesUrl,
                                  HttpMethod.POST,
                                  new HttpEntity<>(modules),
                                  String.class);

        return responseEntity.getBody();
    }

    @Override
    public void insertUserPermissions(Integer userId, List<UserPermission> permissions) {
        Map<String, Object> params = new HashMap<>();
        params.put(USER_ID_KEY, userId);

        URI uri = UriComponentsBuilder.fromHttpUrl(insertUserPermissions)
            .buildAndExpand(params)
            .toUri();

        restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(permissions), String.class);
    }
}
