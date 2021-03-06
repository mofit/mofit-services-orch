package com.mofit.orch.services.impl;

import com.mofit.orch.exceptions.RestTemplateErrorHandler;
import com.mofit.orch.models.ClientProfile;
import com.mofit.orch.services.api.IClientService;
import com.mofit.user.models.Client;
import com.mofit.user.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class ClientService implements IClientService {

    private final RestOperations restTemplate;
    private final ClientSportService clientSportService;

    @Value("${services.user.createClient}")
    String createClientUrl;

    @Value("${services.user.getClientById}")
    String getClientByIdUrl;

    @Value("${services.user.updateClientCityId}")
    String updateClientCityIdUrl;

    @Autowired
    public ClientService(RestTemplateBuilder restTemplateBuilder, ClientSportService clientSportService) {
        restTemplate = restTemplateBuilder.errorHandler(new RestTemplateErrorHandler()).build();
        this.clientSportService = clientSportService;
    }

    @Override
    public Integer createNewClient(ClientProfile clientProfile) {
        ResponseEntity<Integer> createClientResponseEntity =
            restTemplate.exchange(createClientUrl,
                                  HttpMethod.POST,
                                  new HttpEntity<>(clientProfile.getClient()),
                                  Integer.class);

        Integer createdClientId = createClientResponseEntity.getBody();

        if (!clientProfile.getPreferredSports().isEmpty() || clientProfile.getPreferredSports() != null) {
            clientSportService.insertClientSports(createdClientId, clientProfile.getPreferredSports());
        }

        return createdClientId;
    }

    @Override
    public ClientProfile getClientById(Integer clientId) {
        ClientProfile clientProfile = new ClientProfile();
        ParameterizedTypeReference<Client> responseType =
            new ParameterizedTypeReference<Client>() {};
        ResponseEntity<Client> responseEntity = restTemplate.exchange(getClientByIdUrl,
                                                                      HttpMethod.GET, null,
                                                                      responseType, clientId);
        clientProfile.setClient(responseEntity.getBody());
        clientProfile.setPreferredSports(
            clientSportService.getClientSports(clientProfile.getClient().getClientId()));

        return clientProfile;
    }

    @Override
    public void updateClientCityId(Integer clientId, Integer cityId) {
        restTemplate.exchange(updateClientCityIdUrl, HttpMethod.PUT, null, Void.class, clientId, cityId);
    }
}
