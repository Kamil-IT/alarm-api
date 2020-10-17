package com.clock.clockapi.controller;

import com.clock.clockapi.api.v1.Delete;
import com.clock.clockapi.api.v1.Error;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.security.Principal;

public interface UserController<ResponseEntity, RequestEntity> {

    /**
     * Paths: ... , .../
     * Example:
     * page
     * page/
     * @return should return all ResponseEntity
     * @param principal current user
     */
    @GetMapping({"", "/"})
    ResponseEntity getUserDetails(@ApiParam(hidden = true) Principal principal);

    /**
     * Paths: ... , .../
     * Request body needed!
     * Should create ResponseEntity
     * Example:
     * page
     * page/
     * @param entity entity to update
     * @param principal current user
     * @return should return created ResponseEntity
     */
    @PostMapping({"", "/"})
    ResponseEntity post(@RequestBody RequestEntity entity, @ApiParam(hidden = true) Principal principal) throws NotFoundException;

    /**
     * Paths: /{id}
     * Example:
     * page/25
     * @param principal current user
     * @return should return object base on DeleteResponse
     */
    @DeleteMapping({"", "/"})
    Delete delete(@ApiParam(hidden = true) Principal principal) throws NotFoundException;

    /**
     * Should handle exception
     * @param e exception
     * @return object base on ErrorResponse with info about error
     */
    @ExceptionHandler({NotFoundException.class, IllegalArgumentException.class, NullPointerException.class})
    default Error errorHandler(Exception e){
        return Error.builder().message(e.getMessage()).status("404").build();
    }
}
