package com.mofit.orch.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mofit.sport.models.ClientSport;
import com.mofit.user.models.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientProfile {
    private Client client;
    private List<ClientSport> preferredSports;
}
