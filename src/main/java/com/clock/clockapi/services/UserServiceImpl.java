package com.clock.clockapi.services;

import com.clock.clockapi.api.v1.mapper.UserAppMapperService;
import com.clock.clockapi.security.model.UserApp;
import com.clock.clockapi.repository.UserRepository;
import com.clock.clockapi.security.model.UserAppDto;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserAppMapperService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findUserAppByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Not found user witch username = " + username));
    }

    @Named("getUserByUsername")
    @Override
    public UserApp getUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findUserAppByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Not found user witch username = " + username));
    }

    /**
     * Update only given fields
     * @param id user id
     * @param userApp user object
     * @return updated user
     * @throws NotFoundException
     */
    @Override
    public UserApp updateUser(String id, UserAppDto userApp) throws NotFoundException {
        UserApp user = getUserById(id);
        if (userApp.getUsername() != null) user.setUsername(userApp.getUsername());
        if (userApp.getEmail() != null) user.setEmail(userApp.getEmail());
        if (userApp.getPassword() != null) user.setPassword(passwordEncoder.encode(userApp.getPassword()));
        return userRepository.save(user);
    }

    @Named("getUserById")
    @Override
    public UserApp getUserById(String id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found user witch id = " + id));
    }

    @Override
    public UserApp saveUser(UserApp userApp) {
        userApp.setPassword(passwordEncoder.encode(userApp.getPassword()));
        return userRepository.save(userApp);
    }

    @Override
    public void deleteUserById(String id) throws NotFoundException {
        if (!userRepository.existsById(id)){
            throw new NotFoundException("Not found user witch id = " + id);
        }
        userRepository.deleteById(id);
    }

    @Named("getUserById_UserAppObject")
    @Override
    public UserApp getUserById_UserAppObject(UserApp userApp) {
        return userRepository
                .findById(userApp.getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Not found user witch id = " + userApp.getId()));
    }

    @Override
    public List<UserApp> getAllUsers() {
        return userRepository.findAll();
    }
}
