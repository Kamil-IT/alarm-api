package com.clock.clockapi.controller.v1;

import com.clock.clockapi.api.v1.Delete;
import com.clock.clockapi.api.v1.Error;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface BaseController<KeyId, ResponseEntity> {

    /**
     * Paths: ... , .../
     * Example:
     * page
     * page/
     * @return should return all ResponseEntity
     */
    @GetMapping({"", "/"})
    List<ResponseEntity> getAll();

    /**
     * Paths: /{id}
     * Example:
     * page/25
     * @param id entity id
     * @return should return ResponseEntity with id from path /{id}
     */
    @GetMapping("/{id}")
    ResponseEntity getById(@PathVariable("id") KeyId id) throws NotFoundException;

    /**
     * Paths: ... , .../
     * Request body needed!
     * Should create ResponseEntity
     * Example:
     * page
     * page/
     * @param entity entity to create
     * @return should return created ResponseEntity
     */
    @PostMapping({"", "/"})
    ResponseEntity post(@RequestBody ResponseEntity entity);

    /**
     * Paths: ... , .../
     * Request body needed!
     * Should create or update ResponseEntity
     * Example:
     * page
     * page/
     * @param entity entity to create or update
     * @return should return created or updated ResponseEntity
     */
    @PutMapping({"", "/"})
    ResponseEntity put(@RequestBody ResponseEntity entity);

    /**
     * Paths: /{id}
     * Example:
     * page/25
     * @param id id object to delete
     * @return should return object base on DeleteResponse
     */
    @DeleteMapping("/{id}")
    Delete delete(@PathVariable("id") KeyId id) throws NotFoundException;

    /**
     * Should handle exception
     * @param e exception
     * @return object base on ErrorResponse with info about error
     */
    @ExceptionHandler({NotFoundException.class, IllegalArgumentException.class, NullPointerException.class})
    default Error errorHandler(Exception e){
        return Error.builder().message(e.getMessage()).status("404").build();
    };
}
