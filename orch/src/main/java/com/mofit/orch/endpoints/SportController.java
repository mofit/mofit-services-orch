package com.mofit.orch.endpoints;

import com.mofit.orch.services.api.ISportService;
import com.mofit.sport.models.ExperienceLevel;
import com.mofit.sport.models.Sport;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/sports")
public class SportController {

    private ISportService sportService;

    @Autowired
    public SportController(final ISportService sportService) {
        this.sportService = sportService;
    }

    @ApiOperation(value = "Get sports")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Sport> getSports(@RequestParam boolean getActiveOnly) {
        return sportService.getSports(getActiveOnly);
    }

    @ApiOperation(value = "Get sport experience levels")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/experienceLevels")
    public List<ExperienceLevel> getExperienceLevels() {
        return sportService.getExperienceLevels();
    }
}
