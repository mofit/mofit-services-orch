package com.mofit.orch.endpoints;

import com.mofit.media.models.AvatarUploadResponse;
import com.mofit.orch.services.api.IAvatarService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/media")
public class AvatarController {

    private IAvatarService avatarService;

    @Autowired
    public AvatarController(final IAvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @ApiOperation(value = "Upload User Avatar")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/avatar/{userId}")
    public AvatarUploadResponse uploadAvatar(@PathVariable Integer userId,
                                             @RequestPart(required = false) MultipartFile file){

        return avatarService.uploadUserAvatar(userId, file);
    }

    @ApiOperation(value = "Update User Avatar")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/avatar/{userId}")
    public AvatarUploadResponse updateAvatar(@PathVariable Integer userId,
                                             @RequestPart(required = false) MultipartFile file){

        return avatarService.updateUserAvatar(userId, file);
    }

    @ApiOperation(value = "Get User Avatar Data by userId")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/avatar/{userId}")
    public AvatarUploadResponse getAvatarDataByUserId(@PathVariable(required = false) Integer userId) {
        return avatarService.getAvatarData(userId);
    }

    @ApiOperation(value = "Delete User Avatar Data by userId")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/avatar/{userId}")
    public void deleteAvatarDataByUserId(@PathVariable(required = false) Integer userId) {
        avatarService.deleteAvatarData(userId);
    }
}