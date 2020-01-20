package com.mofit.orch.services.impl;

import com.mofit.mainmofitapiservice.models.Trainer;
import com.mofit.orch.services.api.ITrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class TrainerService implements ITrainerService {

    private static final String USER_ID_KEY = "userId";

    private final RestOperations restTemplate;

    @Value("${services.user.createTrainer}")
    String createTrainerUrl;

    @Value("${services.user.getTrainerByUserId}")
    String getTrainerByUserIdUrl;

    @Autowired
    public TrainerService(RestOperations restTemplatе) {
        this.restTemplate = restTemplatе;
    }

    @Override
    public Integer createNewTrainer(Trainer trainer) {
        ResponseEntity<Integer> responseEntity =
            restTemplate.exchange(createTrainerUrl,
                                  HttpMethod.POST,
                                  new HttpEntity<>(trainer),
                                  Integer.class);

        return responseEntity.getBody();
    }

    @Override
    public Trainer getTrainerByUserId(Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(USER_ID_KEY, userId);

        URI uri = UriComponentsBuilder.fromHttpUrl(getTrainerByUserIdUrl)
            .buildAndExpand(params)
            .toUri();

        ResponseEntity<Trainer> responseEntity = restTemplate.exchange(uri,
                                                                      HttpMethod.GET,
                                                                      null,
                                                                      Trainer.class);

        return responseEntity.getBody();

    }
}
