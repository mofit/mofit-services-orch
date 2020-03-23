package com.mofit.orch.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mofit.user.models.GenericErrorResponse;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()))) {
                String httpBodyResponse = reader.lines().collect(Collectors.joining(""));

                ObjectMapper mapper = new ObjectMapper();
                GenericErrorResponse
                    errorResponse = mapper.readValue(httpBodyResponse, GenericErrorResponse.class);

                throw new CustomClientException(errorResponse.getMessage(), response.getStatusCode());
            }
        }
    }
}