package com.mofit.orch.endpoints;

import com.mofit.orch.services.api.IClientSportService;
import com.mofit.sport.models.ClientSport;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/sports/clients")
public class ClientSportController {

    private IClientSportService clientSportService;

    @Autowired
    public ClientSportController(final IClientSportService clientSportService) {
        this.clientSportService = clientSportService;
    }

    @ApiOperation(value = "Insert client sports")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{clientId}")
    public void insertClientSports(@PathVariable Integer clientId,
                                   @RequestBody List<ClientSport> sports) {
        clientSportService.insertClientSports(clientId, sports);
    }

    @ApiOperation(value = "Update client sports")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{clientId}")
    public void updateClientSports(@PathVariable Integer clientId,
                                   @RequestBody List<ClientSport> sports) {
        clientSportService.updateClientSports(clientId, sports);
    }

    @ApiOperation(value = "Get client sports")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{clientId}")
    public List<ClientSport> getClientSports(@PathVariable Integer clientId) {
        return clientSportService.getClientSports(clientId);
    }

    @ApiOperation(value = "Delete client sports")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void deleteClientSports(@RequestBody List<Integer> clientSportIds) {
        clientSportService.deleteClientSports(clientSportIds);
    }
}
