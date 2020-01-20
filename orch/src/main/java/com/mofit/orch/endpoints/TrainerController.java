package com.mofit.orch.endpoints;

import com.mofit.mainmofitapiservice.models.Trainer;
import com.mofit.orch.services.api.ITrainerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class TrainerController {

    private ITrainerService trainerService;

    @Autowired
    public TrainerController(final ITrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @ApiOperation(value = "Creates new Trainer")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/trainer")
    public Integer createNewTrainer(@RequestBody Trainer trainer) {
        return trainerService.createNewTrainer(trainer);
    }

    @ApiOperation(value = "Get Trainer by UserId")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("trainer/{userId}")
    public Trainer getTrainerByUserId(@PathVariable Integer userId) {
        return trainerService.getTrainerByUserId(userId);
    }

}
