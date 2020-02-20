package com.mofit.orch.services.impl;

import com.mofit.mainmofitapiservice.models.CustomClientException;
import com.mofit.media.models.AvatarData;
import com.mofit.orch.exceptions.RestTemplateErrorHandler;
import com.mofit.orch.services.api.IAvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.mofit.orch.services.utils.FileUtils.getUserFileResource;

@Service
public class AvatarService implements IAvatarService {

    private final RestOperations restTemplate;
    private final UserService userService;

    @Value("${services.media.setAvatar}")
    String setAvatarUrl;

    @Value("${spring.http.multipart.max-file-size}")
    Long maxRequestFileSize;

    @Autowired
    public AvatarService(RestTemplateBuilder restTemplateBuilder, UserService userService) {
        this.userService = userService;

        restTemplate = restTemplateBuilder
            .errorHandler(new RestTemplateErrorHandler())
            .build();
    }

    @Override
    public AvatarData uploadUserAvatar(Integer userId, MultipartFile file) {
        verifyFileSize(file);

        userService.getUserByUserId(userId);

        ParameterizedTypeReference<AvatarData> responseType =
            new ParameterizedTypeReference<AvatarData>() {
            };

        ResponseEntity<AvatarData> responseEntity;

        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        try {
            bodyMap.add("file", getUserFileResource(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

        responseEntity = restTemplate.exchange(setAvatarUrl,
                                               HttpMethod.POST,
                                               requestEntity,
                                               responseType, userId);

        return responseEntity.getBody();
    }

    @Override
    public AvatarData updateUserAvatar(Integer userId, MultipartFile file) {
        verifyFileSize(file);

        userService.getUserByUserId(userId);

        ParameterizedTypeReference<AvatarData> responseType =
            new ParameterizedTypeReference<AvatarData>() {
            };

        ResponseEntity<AvatarData> responseEntity;

        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        try {
            bodyMap.add("file", getUserFileResource(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

        responseEntity = restTemplate.exchange(setAvatarUrl,
                                               HttpMethod.PUT,
                                               requestEntity,
                                               responseType, userId);

        return responseEntity.getBody();
    }

    @Override
    public AvatarData getAvatarData(Integer userId) {
        ParameterizedTypeReference<AvatarData> responseType =
            new ParameterizedTypeReference<AvatarData>() {
            };

        ResponseEntity<AvatarData> responseEntity = restTemplate.exchange(
            setAvatarUrl, HttpMethod.GET, null, responseType, userId);

        return responseEntity.getBody();
    }

    @Override
    public void deleteAvatarData(Integer userId) {
        restTemplate.delete(setAvatarUrl, userId);
    }

    private void verifyFileSize(MultipartFile file) {
        if (file.getSize() > maxRequestFileSize) {
            throw new CustomClientException("Requested file exceeds the configured maximum: "
                                            + maxRequestFileSize, HttpStatus.BAD_REQUEST);
        }
    }
}
