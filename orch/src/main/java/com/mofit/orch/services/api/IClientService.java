package com.mofit.orch.services.api;

import com.mofit.orch.models.ClientProfile;

public interface IClientService {

    Integer createNewClient(ClientProfile client);
    ClientProfile getClientById(Integer clientId);
    void updateClientCityId(Integer clientId, Integer cityId);
}
