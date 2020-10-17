package com.clock.clockapi.controller.v1;

import com.clock.clockapi.api.v1.Delete;
import com.clock.clockapi.api.v1.Error;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.clock.clockapi.swagger.SpringFoxConfig.JWT_TOKEN_NAME_SWAGGER;

public interface BaseController<KeyId, ResponseEntity> {

    /**
     * Paths: ... , .../
     * Example:
     * page
     * page/
     * @return should return all ResponseEntity
     * @param principal current user
     */
    @Operation(security = { @SecurityRequirement(name = JWT_TOKEN_NAME_SWAGGER) })
    @GetMapping({"", "/"})
    List<ResponseEntity> getAll(@ApiParam(hidden = true) Principal principal);

    /**
     * Paths: /{id}
     * Example:
     * page/25
     * @param id entity id
     * @param principal current user
     * @return should return ResponseEntity with id from path /{id}
     */
    @Operation(security = { @SecurityRequirement(name = JWT_TOKEN_NAME_SWAGGER) })
    @GetMapping("/{id}")
    ResponseEntity getById(@PathVariable("id") KeyId id, @ApiParam(hidden = true) Principal principal) throws NotFoundException;

    /**
     * Paths: ... , .../
     * Request body needed!
     * Should create ResponseEntity
     * Example:
     * page
     * page/
     * @param entity entity to create
     * @param principal current user
     * @return should return created ResponseEntity
     */
    @Operation(security = { @SecurityRequirement(name = JWT_TOKEN_NAME_SWAGGER) })
    @PostMapping({"", "/"})
    ResponseEntity post(@RequestBody() ResponseEntity entity, @ApiParam(hidden = true) Principal principal);

    /**
     * Paths: ... , .../
     * Request body needed!
     * Should create or update ResponseEntity
     * Example:
     * page
     * page/
     * @param entity entity to create or update
     * @param principal current user
     * @return should return created or updated ResponseEntity
     */
    @Operation(security = { @SecurityRequirement(name = JWT_TOKEN_NAME_SWAGGER) })
    @PutMapping({"", "/"})
    ResponseEntity put(@RequestBody ResponseEntity entity, @ApiParam(hidden = true) Principal principal);

    /**
     * Paths: /{id}
     * Example:
     * page/25
     * @param id id object to delete
     * @param principal current user
     * @return should return object base on DeleteResponse
     */
    @Operation(security = { @SecurityRequirement(name = JWT_TOKEN_NAME_SWAGGER) })
    @DeleteMapping("/{id}")
    Delete delete(@PathVariable("id") KeyId id, @ApiParam(hidden = true) Principal principal) throws NotFoundException;

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
