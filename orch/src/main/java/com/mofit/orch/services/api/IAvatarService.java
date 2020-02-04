package com.mofit.orch.services.api;

import com.mofit.media.models.AvatarUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IAvatarService {
    AvatarUploadResponse uploadUserAvatar(Integer userId, MultipartFile file);
    AvatarUploadResponse updateUserAvatar(Integer userId, MultipartFile file);
}
