package com.mofit.orch.services.api;

import com.mofit.mainmofitapiservice.models.Client;

public interface IClientService {

    Integer createNewClient(Client user);
    Client getClientByUserId(Integer userId);
}
