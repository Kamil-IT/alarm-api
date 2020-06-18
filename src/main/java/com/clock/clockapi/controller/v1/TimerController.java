package com.clock.clockapi.controller.v1;

import com.clock.clockapi.api.v1.Delete;
import com.clock.clockapi.api.v1.modeldto.StopwatchDto;
import com.clock.clockapi.api.v1.modeldto.TimerDto;
import com.clock.clockapi.services.v1.BaseUserFilterService;
import com.clock.clockapi.services.v1.TimerService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("api/v1/timer")
public class TimerController implements BaseController<String, TimerDto>{

    private final BaseUserFilterService<TimerDto, String> timerService;

    /**
     * Paths: ... , .../
     * Example:
     * page
     * page/
     *
     * @return should return all ResponseEntity
     * @param principal current user
     */
    @Override
    public List<TimerDto> getAll(Principal principal) {
        return timerService.getAll(principal.getName());
    }

    /**
     * Paths: /{id}
     * Example:
     * page/25
     *
     * @param s entity id
     * @param principal current user
     * @return should return ResponseEntity with id from path /{id}
     */
    @Override
    public TimerDto getById(String s, Principal principal) throws NotFoundException {
        return timerService.getById(s, principal.getName());
    }

    /**
     * Paths: ... , .../
     * Request body needed!
     * Should create ResponseEntity
     * Example:
     * page
     * page/
     *
     * @param timerDto entity to create
     * @param principal current user
     * @return should return created ResponseEntity
     */
    @Override
    public TimerDto post(TimerDto timerDto, Principal principal) {
        return timerService.post(timerDto, principal.getName());
    }

    /**
     * Paths: ... , .../
     * Request body needed!
     * Should create or update ResponseEntity
     * Example:
     * page
     * page/
     *
     * @param timerDto entity to create or update
     * @param principal current user
     * @return should return created or updated ResponseEntity
     */
    @Override
    public TimerDto put(TimerDto timerDto, Principal principal) {
        return timerService.put(timerDto, principal.getName());
    }

    /**
     * Paths: /{id}
     * Example:
     * page/25
     *
     * @param s id object to delete
     * @param principal current user
     * @return should return object base on DeleteResponse
     */
    @Override
    public Delete delete(String s, Principal principal) throws NotFoundException {
        timerService.delete(s, principal.getName());
        return Delete.builder().status("success").objectId(s).build();
    }
}
