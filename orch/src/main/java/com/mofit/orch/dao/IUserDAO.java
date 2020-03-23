package com.mofit.orch.dao;

import com.mofit.user.models.User;
import java.util.Optional;

public interface IUserDAO {
    Optional<User> getUserByEmail(String email);
}
