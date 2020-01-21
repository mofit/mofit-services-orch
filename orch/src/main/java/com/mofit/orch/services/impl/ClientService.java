package com.mofit.orch.services.impl;

import com.mofit.mainmofitapiservice.models.Client;
import com.mofit.orch.exceptions.RestTemplateErrorHandler;
import com.mofit.orch.services.api.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

//TODO: Investigate if we need to authenticate the token in the service methods
@Service
public class ClientService implements IClientService {

    private static final String USER_ID_KEY = "userId";

    private final RestOperations restTemplate;
    private final RestTemplateBuilder restTemplateBuilder;

    @Value("${services.user.createClient}")
    String createClientUrl;

    @Value("${services.user.getClientByUserId}")
    String getClientByUserIdUrl;

    @Autowired
    public ClientService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
        restTemplate = restTemplateBuilder.errorHandler(new RestTemplateErrorHandler()).build();
    }

    @Override
    public Integer createNewClient(Client client) {
        ResponseEntity<Integer> responseEntity =
            restTemplate.exchange(createClientUrl,
                                  HttpMethod.POST,
                                  new HttpEntity<>(client),
                                  Integer.class);

        return responseEntity.getBody();
    }

    @Override
    public Client getClientByUserId(Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(USER_ID_KEY, userId);

        URI uri = UriComponentsBuilder.fromHttpUrl(getClientByUserIdUrl)
            .buildAndExpand(params)
            .toUri();

        ResponseEntity<Client> responseEntity = restTemplate.exchange(uri,
                                                                      HttpMethod.GET,
                                                                      null,
                                                                      Client.class);

        return responseEntity.getBody();

    }
}
