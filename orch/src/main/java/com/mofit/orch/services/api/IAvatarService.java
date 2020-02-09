package com.mofit.orch.services.api;

import com.mofit.media.models.AvatarData;
import org.springframework.web.multipart.MultipartFile;

public interface IAvatarService {
    AvatarData uploadUserAvatar(Integer userId, MultipartFile file);
    AvatarData updateUserAvatar(Integer userId, MultipartFile file);
    AvatarData getAvatarData(Integer userId);
    void deleteAvatarData(Integer userId);
}
