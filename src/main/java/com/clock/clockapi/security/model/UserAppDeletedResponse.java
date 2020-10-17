package com.clock.clockapi.security.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "User delete response")
public class UserAppDeletedResponse {

    String message;
    UserApp appUser;
}
