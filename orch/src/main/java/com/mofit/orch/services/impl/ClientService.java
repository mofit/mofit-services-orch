package com.mofit.orch.services.impl;

import com.mofit.mainmofitapiservice.models.Client;
import com.mofit.media.models.AvatarData;
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

@Service
public class ClientService implements IClientService {

    private static final String USER_ID_KEY = "userId";

    private final RestOperations restTemplate;
    private final RestTemplateBuilder restTemplateBuilder;
    private final AvatarService avatarService;
    private final ClientSportService clientSportService;


    @Value("${services.user.createClient}")
    String createClientUrl;

    @Value("${services.user.getClientByUserId}")
    String getClientByUserIdUrl;

    @Autowired
    public ClientService(RestTemplateBuilder restTemplateBuilder, AvatarService avatarService, ClientSportService clientSportService) {
        this.restTemplateBuilder = restTemplateBuilder;
        restTemplate = restTemplateBuilder.errorHandler(new RestTemplateErrorHandler()).build();
        this.avatarService = avatarService;
        this.clientSportService = clientSportService;
    }

    @Override
    public Integer createNewClient(Client client) {
        ResponseEntity<Integer> createClientResponseEntity =
            restTemplate.exchange(createClientUrl,
                                  HttpMethod.POST,
                                  new HttpEntity<>(client),
                                  Integer.class);

        Integer createdClientId = createClientResponseEntity.getBody();

        if (!client.getPreferredSports().isEmpty()) {
            clientSportService.insertClientSports(createdClientId, client.getPreferredSports());
        }

        return createdClientId;
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
        Client clientToBeReturned = responseEntity.getBody();

        AvatarData avatarData = avatarService.getAvatarData(userId);

        clientToBeReturned.setThumbnailUrl(avatarData.getThumbnailMediaUrl());
        clientToBeReturned.setAvatarUrl(avatarData.getAvatarMediaUrl());

        clientToBeReturned.setPreferredSports(clientSportService.getClientSports(clientToBeReturned.getClientId()));

        return clientToBeReturned;
    }
}
