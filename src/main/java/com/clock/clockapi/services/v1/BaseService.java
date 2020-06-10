package com.clock.clockapi.services.v1;

import javassist.NotFoundException;

import java.util.List;

public interface BaseService<Entity, Id> {

    List<Entity> getAll();

    Entity getById(Id id) throws NotFoundException;

    Entity post(Entity entity);

    Entity put(Entity entity);

    void delete(Id id) throws NotFoundException;

    default String notFoundMessage(String entityName, Id id){
        return "Not found " + entityName + " witch id = " + id;
    }

    default String idExistInDb(String entityName, Id id){
        return "Entity " + entityName + " witch id = " + id + " already exist in db";
    }
}
