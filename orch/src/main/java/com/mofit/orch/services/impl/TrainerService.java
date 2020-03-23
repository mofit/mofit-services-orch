package com.mofit.orch.services.impl;

import com.mofit.orch.exceptions.RestTemplateErrorHandler;
import com.mofit.orch.services.api.ITrainerService;
import com.mofit.user.models.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class TrainerService implements ITrainerService {

    private final RestOperations restTemplate;

    @Value("${services.user.createTrainer}")
    String createTrainerUrl;

    @Value("${services.user.getTrainerById}")
    String getTrainerByIdUrl;

    @Autowired
    public TrainerService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.errorHandler(new RestTemplateErrorHandler()).build();
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
    public Trainer getTrainerById(Integer trainerId) {
        ResponseEntity<Trainer> responseEntity = restTemplate.exchange(getTrainerByIdUrl,
                                                                      HttpMethod.GET, null,
                                                                       Trainer.class, trainerId);

        return responseEntity.getBody();
    }
}
