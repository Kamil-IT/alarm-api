package com.clock.clockapi.controller.v1;

import com.clock.clockapi.api.v1.Delete;
import com.clock.clockapi.api.v1.modeldto.AlarmDto;
import com.clock.clockapi.services.v1.AlarmService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/alarm")
public class AlarmController implements BaseController<String, AlarmDto> {

    private final AlarmService alarmService;

    /**
     * Paths: ... , .../
     * Example:
     * page
     * page/
     *
     * @return should return all ResponseEntity
     */
    @Override
    public List<AlarmDto> getAll() {
        return alarmService.getAll();
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
    public AlarmDto getById(String s) throws NotFoundException {
        return alarmService.getById(s);
    }

    /**
     * Paths: ... , .../
     * Request body needed!
     * Should create ResponseEntity
     * Example:
     * page
     * page/
     *
     * @param alarmDto entity to create
     * @return should return created ResponseEntity
     */
    @Override
    public AlarmDto post(AlarmDto alarmDto) {
        return alarmService.post(alarmDto);
    }

    /**
     * Paths: ... , .../
     * Request body needed!
     * Should create or update ResponseEntity
     * Example:
     * page
     * page/
     *
     * @param alarmDto entity to create or update
     * @return should return created or updated ResponseEntity
     */
    @Override
    public AlarmDto put(AlarmDto alarmDto) {
        return alarmService.put(alarmDto);
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
        alarmService.delete(s);
        return Delete.builder().status("success").objectId(s).build();
    }
}
