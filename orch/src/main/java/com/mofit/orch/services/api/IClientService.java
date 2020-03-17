package com.mofit.orch.services.api;

import com.mofit.mainmofitapiservice.models.Client;
import com.mofit.orch.models.ClientProfile;

public interface IClientService {

    Integer createNewClient(ClientProfile user);
    ClientProfile getClientByUserId(Integer userId);
}
