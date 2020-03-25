package com.mofit.orch.endpoints;

import com.mofit.orch.models.ClientProfile;
import com.mofit.orch.services.api.IClientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin // TODO: Investigate if we need CORS for all controllers/endpoint and if yes, set global
@RequestMapping("/clients")
public class ClientController {

    private IClientService clientService;

    @Autowired
    public ClientController(final IClientService clientService) {
        this.clientService = clientService;
    }

    @ApiOperation(value = "Creates new Client")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Integer createNewClient(@RequestBody ClientProfile client) {
        return clientService.createNewClient(client);
    }

    @ApiOperation(value = "Get Client by UserId")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{clientId}")
    public ClientProfile getClientByUserId(@PathVariable Integer clientId) {
        return clientService.getClientById(clientId);
    }

    @ApiOperation(value = "Update cityId for client by clientId")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{clientId}/city/{cityId}")
    public void updateClientCityId(@PathVariable Integer clientId, @PathVariable Integer cityId) {
        clientService.updateClientCityId(clientId, cityId);
    }
}