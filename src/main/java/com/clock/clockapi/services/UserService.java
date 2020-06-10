package com.clock.clockapi.services;

import com.clock.clockapi.security.model.UserApp;
import javassist.NotFoundException;

import java.util.List;

public interface UserService {

    List<UserApp> getAllUsers();

    UserApp getUserById(String id) throws NotFoundException;

    UserApp saveUser(UserApp userApp);

    void deleteUserById(String id) throws NotFoundException;
}
