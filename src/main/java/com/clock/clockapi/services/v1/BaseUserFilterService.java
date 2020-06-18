package com.clock.clockapi.services.v1;

import javassist.NotFoundException;

import java.util.List;

public interface BaseUserFilterService<Entity, Id> {

    List<Entity> getAll(String username);

    Entity getById(Id id, String username) throws NotFoundException;

    Entity post(Entity entity, String username);

    Entity put(Entity entity, String username);

    void delete(Id id, String username) throws NotFoundException;

    default String failurePermissionMessage(String required, String actual){
        return String.format("Not enough permission! Required %s, Actual %s", required, actual);
    }
}
