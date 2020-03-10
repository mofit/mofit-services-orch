package com.mofit.orch.services.impl;

import com.mofit.orch.exceptions.RestTemplateErrorHandler;
import com.mofit.orch.services.api.IClientSportService;
import com.mofit.sport.models.ClientSport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.List;

@Service
public class ClientSportService implements IClientSportService {

    private final RestOperations restTemplate;
    private final RestTemplateBuilder restTemplateBuilder;

    @Value("${services.sport.getClientSports}")
    String getClientSportsUrl;

    @Value("${services.sport.deleteClientSports}")
    String deleteClientSportsUrl;

    @Autowired
    public ClientSportService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
        restTemplate = restTemplateBuilder.errorHandler(new RestTemplateErrorHandler()).build();
    }

    @Override
    public void insertClientSports(Integer clientId, List<ClientSport> clientSports) {
        ParameterizedTypeReference<Void> responseType =
            new ParameterizedTypeReference<Void>() {
            };

        restTemplate.exchange(getClientSportsUrl,
                              HttpMethod.POST,
                              new HttpEntity<>(clientSports),
                              responseType, clientId);
    }

    @Override
    public List<ClientSport> getClientSports(Integer clientId) {
        ParameterizedTypeReference<List<ClientSport>> responseType =
            new ParameterizedTypeReference<List<ClientSport>>() {
            };

        ResponseEntity<List<ClientSport>> responseEntity =
            restTemplate.exchange(getClientSportsUrl, HttpMethod.GET, null, responseType, clientId);

        return responseEntity.getBody();
    }

    @Override
    public void updateClientSports(Integer clientId, List<ClientSport> clientSports) {
        ParameterizedTypeReference<Void> responseType =
            new ParameterizedTypeReference<Void>() {
            };

        restTemplate.exchange(getClientSportsUrl,
                              HttpMethod.PUT,
                              new HttpEntity<>(clientSports),
                              responseType, clientId);
    }

    @Override
    public void deleteClientSports(List<Integer> clientSportIds) {
        ParameterizedTypeReference<Void> responseType =
            new ParameterizedTypeReference<Void>() {
            };

        restTemplate.exchange(deleteClientSportsUrl,
                              HttpMethod.DELETE,
                              new HttpEntity<>(clientSportIds),
                              responseType);
    }
}
