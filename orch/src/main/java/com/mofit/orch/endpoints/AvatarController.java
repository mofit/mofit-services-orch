package com.mofit.orch.endpoints;

import com.mofit.orch.services.api.IAvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/media")
public class AvatarController {

    private IAvatarService avatarService;

    @Autowired
    public AvatarController(final IAvatarService avatarService) {
        this.avatarService = avatarService;
    }

//    @ApiOperation(value = "Upload User Avatar")
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/avatar/{userId}")
//    public AvatarUploadResponse uploadAvatar(@PathVariable Integer userId,
//                                             @RequestPart(required = false) MultipartFile file){
//
//        return avatarService.uploadUserAvatar(userId, file);
//    }
//
//    @ApiOperation(value = "Update User Avatar")
//    @ResponseStatus(HttpStatus.OK)
//    @PutMapping("/avatar/{userId}")
//    public AvatarUploadResponse updateAvatar(@PathVariable Integer userId,
//                                             @RequestPart(required = false) MultipartFile file){
//
//        return avatarService.updateUserAvatar(userId, file);
//    }
}