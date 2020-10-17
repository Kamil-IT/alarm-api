package com.clock.clockapi.controller;

import com.clock.clockapi.api.v1.Delete;
import com.clock.clockapi.security.model.UserApp;
import com.clock.clockapi.security.model.UserAppDto;
import com.clock.clockapi.services.UserServiceImpl;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserControllerImpl implements UserController<UserApp, UserAppDto> {

    private UserServiceImpl userService;

    @Override
    public UserApp getUserDetails(Principal principal) {
        return userService.getUserByUsername(principal.getName());
    }

    @Override
    public UserApp post(UserAppDto userApp, Principal principal) throws NotFoundException {
        String userId = userService.getUserByUsername(principal.getName()).getId();
        return userService.updateUser(userId, userApp);
    }

    @Override
    public Delete delete(Principal principal) {
        try {
            userService.deleteUserById(
                    userService.getUserByUsername(principal.getName()).getId());
        } catch (NotFoundException e){
            return new Delete("404", "User with username " + principal.getName());
        }
        return new Delete("200", "Deleted user with username " + principal.getName());
    }
}
