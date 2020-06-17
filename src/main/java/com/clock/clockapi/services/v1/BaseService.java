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
        return String.format("Not found %s witch id = %s", entityName, id);
    }

    default String idExistInDb(String entityName, Id id){
        return String.format("Entity %s witch id = %s already exist in db", entityName, id);
    }
}
