package com.clock.clockapi.controller.v1;

import com.clock.clockapi.api.v1.Delete;
import com.clock.clockapi.api.v1.modeldto.TimerDto;
import com.clock.clockapi.services.v1.TimerService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/timer")
public class TimerController implements BaseController<String, TimerDto>{

    private final TimerService timerService;

    /**
     * Paths: ... , .../
     * Example:
     * page
     * page/
     *
     * @return should return all ResponseEntity
     */
    @Override
    public List<TimerDto> getAll() {
        return timerService.getAll();
    }

    /**
     * Paths: /{id}
     * Example:
     * page/25
     *
     * @param s entity id
     * @return should return ResponseEntity with id from path /{id}
     */
    @Override
    public TimerDto getById(String s) throws NotFoundException {
        return timerService.getById(s);
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
     * @return should return created ResponseEntity
     */
    @Override
    public TimerDto post(TimerDto timerDto) {
        return timerService.post(timerDto);
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
     * @return should return created or updated ResponseEntity
     */
    @Override
    public TimerDto put(TimerDto timerDto) {
        return timerService.put(timerDto);
    }

    /**
     * Paths: /{id}
     * Example:
     * page/25
     *
     * @param s id object to delete
     * @return should return object base on DeleteResponse
     */
    @Override
    public Delete delete(String s) throws NotFoundException {
        timerService.delete(s);
        return Delete.builder().status("success").objectId(s).build();
    }
}
