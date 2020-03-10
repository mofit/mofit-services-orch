package com.mofit.orch.services.impl;

import com.mofit.orch.exceptions.RestTemplateErrorHandler;
import com.mofit.orch.services.api.ISportService;
import com.mofit.sport.models.Sport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class SportService implements ISportService {

    private RestOperations restTemplate;

    @Value("${services.user.getSports}")
    String getSportsUrl;

    @Autowired
    public SportService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.errorHandler(new RestTemplateErrorHandler()).build();
    }

    @Override
    public List<Sport> getSports(boolean getActiveOnly) {
        ParameterizedTypeReference<List<Sport>> responseType = new ParameterizedTypeReference<List<Sport>>() {};

        UriComponents UriBuilder = UriComponentsBuilder
            .fromUriString(getSportsUrl)
            .queryParam("getActiveOnly", getActiveOnly).build();

        return restTemplate.exchange(UriBuilder.toUriString(),
                                    HttpMethod.GET,
                                    null,
                                    responseType).getBody();
    }
}
