package com.clock.clockapi.services.v1;

import com.clock.clockapi.api.v1.mapper.StopwatchMapper;
import com.clock.clockapi.api.v1.model.alarm.Alarm;
import com.clock.clockapi.api.v1.model.stopwatch.Stopwatch;
import com.clock.clockapi.api.v1.modeldto.StopwatchDto;
import com.clock.clockapi.repository.UserRepository;
import com.clock.clockapi.repository.v1.StopwatchRepository;
import com.clock.clockapi.security.model.UserApp;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StopwatchService implements BaseService<StopwatchDto, String>, BaseUserFilterService<StopwatchDto, String> {

    private final StopwatchRepository stopwatchRepository;
    private final UserRepository userRepository;
    private final StopwatchMapper stopwatchMapper;

    @Override
    public List<StopwatchDto> getAll() {
        return stopwatchRepository.findAll()
                .stream()
                .map(stopwatchMapper::stopwatchToStopwatchDto)
                .collect(Collectors.toList());
    }

    @Override
    public StopwatchDto getById(String id) throws NotFoundException {
        return stopwatchMapper.stopwatchToStopwatchDto(
                stopwatchRepository.findById(id)
                        .orElseThrow(() -> new
                                NotFoundException(notFoundMessage("Stopwatch", id))));
    }

    @Override
    public StopwatchDto post(StopwatchDto stopwatchDto) {
        if (stopwatchDto.getId() != null) {
            if (stopwatchRepository.existsById(stopwatchDto.getId())) {
                throw new IllegalArgumentException(idExistInDbMessage("Stopwatch", stopwatchDto.getId()));
            }
        }
        Stopwatch saveEntity = stopwatchRepository.save(stopwatchMapper.stopwatchDtoToStopwatch(stopwatchDto));

        return stopwatchMapper.stopwatchToStopwatchDto(saveEntity);
    }

    @Override
    public StopwatchDto put(StopwatchDto stopwatchDto) {
        Stopwatch saveEntity = stopwatchRepository.save(stopwatchMapper.stopwatchDtoToStopwatch(stopwatchDto));

        return stopwatchMapper.stopwatchToStopwatchDto(saveEntity);
    }

    @Override
    public void delete(String id) throws NotFoundException {
        if (stopwatchRepository.existsById(id)) {
            stopwatchRepository.deleteById(id);
        } else {
            throw new NotFoundException(notFoundMessage("Stopwatch", id));
        }
    }

    @Override
    public List<StopwatchDto> getAll(String username) {
        return stopwatchRepository.findAllByUserId(getUserIdByUsername(username))
                .stream()
                .map(stopwatchMapper::stopwatchToStopwatchDto)
                .collect(Collectors.toList());
    }

    @Override
    public StopwatchDto getById(String id, String username) throws NotFoundException {
        return stopwatchMapper.stopwatchToStopwatchDto(
                stopwatchRepository.findByIdAndUserId(id, getUserIdByUsername(username))
                        .orElseThrow(() -> new
                                NotFoundException(notFoundMessage("Stopwatch", id))));
    }

    @Override
    public StopwatchDto post(StopwatchDto stopwatchDto, String username) {
        UserApp userFromFunction = userRepository.findUserAppByUsername(username).orElseThrow(() -> new
                IllegalArgumentException(notFoundMessage("User", username)));

        if (stopwatchDto.getId() != null) {
            if (stopwatchRepository.existsById(stopwatchDto.getId())) {
                throw new IllegalArgumentException(idExistInDbMessage("Stopwatch", stopwatchDto.getId()));
            }
            if (!stopwatchDto.getUserApp().getId().equals(userFromFunction.getId())) {
                throw new IllegalArgumentException(
                        failurePermissionMessage(stopwatchDto.getUserApp().getId(), username));
            }
        }
        stopwatchDto.setUserApp(userFromFunction);
        Stopwatch saveEntity = stopwatchRepository.save(stopwatchMapper.stopwatchDtoToStopwatch(stopwatchDto));

        return stopwatchMapper.stopwatchToStopwatchDto(saveEntity);
    }

    @Override
    public StopwatchDto put(StopwatchDto stopwatchDto, String username) {
        UserApp userFromFunction = userRepository.findUserAppByUsername(username).orElseThrow(() -> new
                IllegalArgumentException(notFoundMessage("User", username)));

        if (stopwatchDto.getId() != null) {
            if (!stopwatchDto.getUserApp().getId().equals(userFromFunction.getId())) {
                throw new IllegalArgumentException(
                        failurePermissionMessage(stopwatchDto.getUserApp().getId(), username));
            }
        }

        stopwatchDto.setUserApp(userFromFunction);
        Stopwatch saveEntity = stopwatchRepository.save(stopwatchMapper.stopwatchDtoToStopwatch(stopwatchDto));

        return stopwatchMapper.stopwatchToStopwatchDto(saveEntity);
    }

    @Override
    public void delete(String id, String username) throws NotFoundException {
        Stopwatch stopwatchToDelete = stopwatchRepository.findById(id).orElseThrow(() ->
                new NotFoundException(notFoundMessage("alarm", id)));

        if (!stopwatchToDelete.getUserId().equals(getUserIdByUsername(username))) {
            throw new IllegalArgumentException(
                    failurePermissionMessage(stopwatchToDelete.getUserId(), username));

        }
        stopwatchRepository.deleteById(id);
    }

    private String getUserIdByUsername(String username) {
        return userRepository.findUserAppByUsername(username)
                .orElseThrow(() ->
                        new IllegalArgumentException(notFoundMessage("User", username))).getId();
    }
}
