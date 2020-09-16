package com.clock.clockapi.controller.v1;

import com.clock.clockapi.api.v1.Delete;
import com.clock.clockapi.api.v1.modeldto.StopwatchDto;
import com.clock.clockapi.services.v1.BaseUserFilterService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

//@AllArgsConstructor
//@RestController
//@PreAuthorize("isAuthenticated()")
//@RequestMapping("api/v1/stopwatch")
//public class StopwatchController implements BaseController<String, StopwatchDto>{
//
//    private final BaseUserFilterService<StopwatchDto, String> stopwatchService;
//
//
//    /**
//     * Paths: ... , .../
//     * Example:
//     * page
//     * page/
//     *
//     * @return should return all ResponseEntity
//     * @param principal current user
//     */
//    @Override
//    public List<StopwatchDto> getAll(Principal principal) {
//        return stopwatchService.getAll(principal.getName());
//    }
//
//    /**
//     * Paths: /{id}
//     * Example:
//     * page/25
//     *
//     * @param s entity id
//     * @param principal current user
//     * @return should return ResponseEntity with id from path /{id}
//     */
//    @Override
//    public StopwatchDto getById(String s, Principal principal) throws NotFoundException {
//        return stopwatchService.getById(s, principal.getName());
//    }
//
//    /**
//     * Paths: ... , .../
//     * Request body needed!
//     * Should create ResponseEntity
//     * Example:
//     * page
//     * page/
//     *
//     * @param stopwatchDto entity to create
//     * @param principal current user
//     * @return should return created ResponseEntity
//     */
//    @Override
//    public StopwatchDto post(StopwatchDto stopwatchDto, Principal principal) {
//        return stopwatchService.post(stopwatchDto, principal.getName());
//    }
//
//    /**
//     * Paths: ... , .../
//     * Request body needed!
//     * Should create or update ResponseEntity
//     * Example:
//     * page
//     * page/
//     *
//     * @param stopwatchDto entity to create or update
//     * @param principal current user
//     * @return should return created or updated ResponseEntity
//     */
//    @Override
//    public StopwatchDto put(StopwatchDto stopwatchDto, Principal principal) {
//        return stopwatchService.put(stopwatchDto, principal.getName());
//    }
//
//    /**
//     * Paths: /{id}
//     * Example:
//     * page/25
//     *
//     * @param s id object to delete
//     * @param principal current user
//     * @return should return object base on DeleteResponse
//     */
//    @Override
//    public Delete delete(String s, Principal principal) throws NotFoundException {
//        stopwatchService.delete(s, principal.getName());
//        return Delete.builder().status("success").objectId(s).build();
//    }
//}
