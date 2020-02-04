package com.mofit.orch.services.impl;

import com.mofit.media.models.AvatarUploadResponse;
import com.mofit.orch.exceptions.RestTemplateErrorHandler;
import com.mofit.orch.services.api.IAvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AvatarService implements IAvatarService {

    private final RestOperations restTemplate;
    private final RestTemplateBuilder restTemplateBuilder;

    @Value("${services.media.setAvatar}")
    String setAvatarUrl;

    @Autowired
    public AvatarService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
        restTemplate = restTemplateBuilder
            .errorHandler(new RestTemplateErrorHandler())
            .build();
    }

    @Override
    public AvatarUploadResponse uploadUserAvatar(Integer userId, MultipartFile file) {
        ParameterizedTypeReference<AvatarUploadResponse> responseType =
            new ParameterizedTypeReference<AvatarUploadResponse>() {
            };

        ResponseEntity<AvatarUploadResponse> responseEntity = null;

        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.MULTIPART_FORM_DATA);

        File fileToTransfer = new File("Test");
        try {
            file.transferTo(fileToTransfer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("file", new FileSystemResource(fileToTransfer.getPath()));

        responseEntity = restTemplate.exchange(setAvatarUrl,
                                               HttpMethod.POST,
                                               null,
                                               responseType, params);

        return responseEntity.getBody();
    }

    @Override
    public AvatarUploadResponse updateUserAvatar(Integer userId, MultipartFile file) {
        ParameterizedTypeReference<AvatarUploadResponse> responseType =
            new ParameterizedTypeReference<AvatarUploadResponse>() {
            };

        ResponseEntity<AvatarUploadResponse> responseEntity =
            restTemplate.exchange(setAvatarUrl,
                                  HttpMethod.POST,
                                  new HttpEntity<>(file),
                                  responseType);

        return responseEntity.getBody();
    }
}
