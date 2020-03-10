package com.mofit.orch.services.api;

import com.mofit.sport.models.ClientSport;

import java.util.List;

public interface IClientSportService {
    void insertClientSports(Integer clientId, List<ClientSport> clientSports);
    List<ClientSport> getClientSports(Integer clientId);
    void updateClientSports(Integer clientId, List<ClientSport> clientSports);
    void deleteClientSports(List<Integer> clientSportIds);
}
