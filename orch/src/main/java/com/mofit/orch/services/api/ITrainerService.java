package com.mofit.orch.services.api;

import com.mofit.mainmofitapiservice.models.Trainer;

public interface ITrainerService {
    Integer createNewTrainer(Trainer trainer);
    Trainer getTrainerByUserId(Integer userId);
}
