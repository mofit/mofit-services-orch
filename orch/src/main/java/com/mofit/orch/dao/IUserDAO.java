package com.mofit.orch.dao;


import com.mofit.mainmofitapiservice.models.User;

import java.util.Optional;

public interface IUserDAO {
    Optional<User> getUserByEmail(String email);
}
