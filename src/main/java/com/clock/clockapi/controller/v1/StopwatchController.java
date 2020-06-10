package com.clock.clockapi.controller.v1;

import com.clock.clockapi.api.v1.Delete;
import com.clock.clockapi.api.v1.modeldto.StopwatchDto;
import com.clock.clockapi.services.v1.StopwatchService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/stopwatch")
public class StopwatchController implements BaseController<String, StopwatchDto>{

    private final StopwatchService stopwatchService;


    /**
     * Paths: ... , .../
     * Example:
     * page
     * page/
     *
     * @return should return all ResponseEntity
     */
    @Override
    public List<StopwatchDto> getAll() {
        return stopwatchService.getAll();
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
    public StopwatchDto getById(String s) throws NotFoundException {
        return stopwatchService.getById(s);
    }

    /**
     * Paths: ... , .../
     * Request body needed!
     * Should create ResponseEntity
     * Example:
     * page
     * page/
     *
     * @param stopwatchDto entity to create
     * @return should return created ResponseEntity
     */
    @Override
    public StopwatchDto post(StopwatchDto stopwatchDto) {
        return stopwatchService.post(stopwatchDto);
    }

    /**
     * Paths: ... , .../
     * Request body needed!
     * Should create or update ResponseEntity
     * Example:
     * page
     * page/
     *
     * @param stopwatchDto entity to create or update
     * @return should return created or updated ResponseEntity
     */
    @Override
    public StopwatchDto put(StopwatchDto stopwatchDto) {
        return stopwatchService.put(stopwatchDto);
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
        stopwatchService.delete(s);
        return Delete.builder().status("success").objectId(s).build();
    }
}
