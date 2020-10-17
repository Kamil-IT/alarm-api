package com.clock.clockapi.controller.v1;

import com.clock.clockapi.api.v1.Delete;
import com.clock.clockapi.api.v1.modeldto.AlarmDto;
import com.clock.clockapi.services.v1.BaseUserFilterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static com.clock.clockapi.swagger.SpringFoxConfig.JWT_TOKEN_NAME_SWAGGER;

@AllArgsConstructor
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("api/v1/alarm")
@Api(tags = "Alarm manager")
public class AlarmController implements BaseController<String, AlarmDto> {

    private final BaseUserFilterService<AlarmDto, String> alarmService;

    /**
     * Paths: ... , .../
     * Example:
     * page
     * page/
     *
     * @param principal current user
     * @return should return all ResponseEntity
     */
    @ApiOperation("Get all alarms")
    @Override
    public List<AlarmDto> getAll(@ApiParam(hidden = true) Principal principal) {
        return alarmService.getAll(principal.getName());
    }

    /**
     * Paths: /{id}
     * Example:
     * page/25
     *
     * @param s         entity id
     * @param principal current user
     * @return should return ResponseEntity with id from path /{id}
     */
    @ApiOperation("Get alarm by id")
    @Override
    public AlarmDto getById(String s, Principal principal) throws NotFoundException {
        return alarmService.getById(s, principal.getName());
    }

    /**
     * Paths: ... , .../
     * Request body needed!
     * Should create ResponseEntity
     * Example:
     * page
     * page/
     *
     * @param alarmDto  entity to create
     * @param principal current user
     * @return should return created ResponseEntity
     */
    @ApiOperation("Post alarm")
    @Override
    public AlarmDto post(@ApiParam(name = "Alarm", value = "Alarm") AlarmDto alarmDto, Principal principal) {
        return alarmService.post(alarmDto, principal.getName());
    }

    /**
     * Paths: ... , .../
     * Request body needed!
     * Should create or update ResponseEntity
     * Example:
     * page
     * page/
     *
     * @param alarmDto  entity to create or update
     * @param principal current user
     * @return should return created or updated ResponseEntity
     */
    @ApiOperation("Put alarm")
    @Override
    public AlarmDto put(@ApiParam(name = "Alarm", value = "Alarm") AlarmDto alarmDto, Principal principal) {
        return alarmService.put(alarmDto, principal.getName());
    }

    /**
     * Paths: /{id}
     * Example:
     * page/25
     *
     * @param s         id object to delete
     * @param principal current user
     * @return should return object base on DeleteResponse
     */
    @ApiOperation("Delete alarm by id")
    @Override
    public Delete delete(String s, Principal principal) throws NotFoundException {
        alarmService.delete(s, principal.getName());
        return Delete.builder().status("success").objectId(s).build();
    }
}
