package com.clock.clockapi.repository;

import com.clock.clockapi.security.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserApp, String> {

    Optional<UserApp> findUserAppByUsername(String username);
}
