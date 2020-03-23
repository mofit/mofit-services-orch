package com.mofit.orch.services.api;

import com.mofit.user.models.Trainer;

public interface ITrainerService {
    Integer createNewTrainer(Trainer trainer);
    Trainer getTrainerById(Integer trainerId);
}
