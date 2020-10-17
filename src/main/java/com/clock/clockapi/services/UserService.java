package com.clock.clockapi.services;

import com.clock.clockapi.security.model.UserApp;
import com.clock.clockapi.security.model.UserAppDto;
import javassist.NotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {

    List<UserApp> getAllUsers();

    UserApp getUserById(String id) throws NotFoundException;

    UserApp saveUser(UserApp userApp);

    void deleteUserById(String id) throws NotFoundException;

    UserApp getUserByUsername(String username) throws UsernameNotFoundException;

    UserApp updateUser(String id, UserAppDto userApp) throws NotFoundException;
}
