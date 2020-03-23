package com.mofit.orch.endpoints;

import com.mofit.orch.services.api.ICityService;
import com.mofit.user.models.City;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/cities")
public class CityController {

    private ICityService cityService;

    @Autowired
    public CityController(final ICityService cityService) {
        this.cityService = cityService;
    }

    @ApiOperation(value = "Get cities")
    @CrossOrigin
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<City> getCities() {
        return cityService.getAllCities();
    }
}
