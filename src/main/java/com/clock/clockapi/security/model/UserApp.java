package com.clock.clockapi.security.model;

import com.clock.clockapi.api.v1.model.BaseEntity;
import com.clock.clockapi.api.v1.model.alarm.Alarm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Data
@Entity
@Table(name = "Users")
@ApiModel(value = "User response")
public class UserApp extends BaseEntity implements UserDetails {

    @NotNull
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "account_non_expired", columnDefinition = "boolean default true", nullable = false)
    private boolean accountNonExpired;

    @NotNull
    @Column(name = "account_non_locked", columnDefinition = "boolean default true", nullable = false)
    private boolean accountNonLocked;

    @NotNull
    @Column(name = "credentials_non_expired", columnDefinition = "boolean default true", nullable = false)
    private boolean credentialsNonExpired;

    @NotNull
    @Column(name = "enabled", columnDefinition = "boolean default true", nullable = false)
    private boolean enabled;

    @Nullable
    private String email;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    public void setAccountWorkingInServer(){
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    @OneToMany(mappedBy = "userApp", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Alarm> alarms;

    @JsonIgnore
    public List<Alarm> getAlarms() {
        return alarms;
    }

    @JsonIgnore
    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }
}
