package com.clock.clockapi.security.model;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "User credentials")
public class UserAppDto {

    @NotNull
    @ApiModelProperty(required = true)
    private String username;

    @NotNull
    @ApiModelProperty(required = true)
    private String password;

    @Nullable
    private String email;

    public UserAppDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
