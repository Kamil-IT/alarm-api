package com.clock.clockapi.services;

import com.clock.clockapi.api.v1.mapper.UserAppMapperService;
import com.clock.clockapi.security.model.UserApp;
import com.clock.clockapi.api.v1.model.alarm.Alarm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAppServicesImpl implements UserAppMapperService {
    List<UserApp> usersApp;

    public UserAppServicesImpl() {

        Alarm alarm = new Alarm();
        alarm.setName("New alarm");

        UserApp user1 = new UserApp();
        user1.setId("1234");

        UserApp user2 = new UserApp();
        user2.setId("4321");

        UserApp user3 = new UserApp();
        user3.setId("1111");

        this.usersApp = List.of(user1,user2,user3);
    }

    public UserApp getUserById(String id){
        return usersApp.stream()
                .filter((user) -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Not found user witch id = " + id));
    }

    public UserApp getUserById_UserAppObject(UserApp userApp){
        // This is for tests!!! Dont delete it
        if (userApp.getId().equals("1234")){
            UserApp testUser = new UserApp();
            testUser.setId("1234");
            return testUser;
        }
        return usersApp.stream()
                .filter((user) -> user.getId().equals(userApp.getId()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Not found user witch id = " + userApp.getId()));
    }
}
