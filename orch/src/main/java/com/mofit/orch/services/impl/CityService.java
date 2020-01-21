package com.mofit.orch.services.impl;

import com.mofit.mainmofitapiservice.models.City;
import com.mofit.orch.exceptions.RestTemplateErrorHandler;
import com.mofit.orch.services.api.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.List;

@Service
public class CityService implements ICityService {

    private final RestOperations restTemplate;
    private final RestTemplateBuilder restTemplateBuilder;

    @Value("${services.user.getCities}")
    String getCitiesUrl;

    @Autowired
    public CityService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
        restTemplate = restTemplateBuilder.errorHandler(new RestTemplateErrorHandler()).build();
    }

    @Override
    public List<City> getAllCities() {
        ParameterizedTypeReference<List<City>> responseType =
            new ParameterizedTypeReference<List<City>>() {
            };

            return restTemplate.exchange(getCitiesUrl,
                                  HttpMethod.GET,
                                  null,
                                  responseType).getBody();
    }
}
