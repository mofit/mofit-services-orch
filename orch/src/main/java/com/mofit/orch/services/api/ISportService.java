package com.mofit.orch.services.api;


import com.mofit.sport.models.Sport;

import java.util.List;

public interface ISportService {

    List<Sport> getSports(boolean getActiveOnly);
}
