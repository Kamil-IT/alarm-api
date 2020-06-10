package com.clock.clockapi.security.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAppDto {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @Nullable
    private String email;

    public UserAppDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
