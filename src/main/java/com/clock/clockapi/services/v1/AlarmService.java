package com.clock.clockapi.services.v1;

import com.clock.clockapi.api.v1.mapper.AlarmMapper;
import com.clock.clockapi.api.v1.model.alarm.Alarm;
import com.clock.clockapi.api.v1.modeldto.AlarmDto;
import com.clock.clockapi.repository.UserRepository;
import com.clock.clockapi.repository.v1.AlarmRepository;
import com.clock.clockapi.security.model.UserApp;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AlarmService implements BaseService<AlarmDto, String>, BaseUserFilterService<AlarmDto, String> {

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final AlarmMapper alarmMapper;

    @Override
    public List<AlarmDto> getAll() {
        return alarmRepository.findAll()
                .stream()
                .map(alarmMapper::alarmToAlarmDto)
                .collect(Collectors.toList());
    }

    @Override
    public AlarmDto getById(String id) throws NotFoundException {
        return alarmMapper.alarmToAlarmDto(
                alarmRepository.findById(id)
                        .orElseThrow(() ->
                                new NotFoundException(notFoundMessage("alarm", id))));
    }

    @Override
    public AlarmDto post(AlarmDto alarmDto) {
        if (alarmDto.getId() != null) {
            if (alarmRepository.existsById(alarmDto.getId())) {
                throw new IllegalArgumentException(idExistInDbMessage("Stopwatch", alarmDto.getId()));
            }
        }
        Alarm saveAlarm = alarmRepository.save(alarmMapper.alarmDtoToAlarm(alarmDto));

        return alarmMapper.alarmToAlarmDto(saveAlarm);
    }

    @Override
    public AlarmDto put(AlarmDto alarmDto) {
        Alarm saveAlarm = alarmRepository.save(alarmMapper.alarmDtoToAlarm(alarmDto));

        return alarmMapper.alarmToAlarmDto(saveAlarm);
    }

    @Override
    public void delete(String id) throws NotFoundException {
        if (alarmRepository.existsById(id)) {
            alarmRepository.deleteById(id);
        } else {
            throw new NotFoundException(notFoundMessage("alarm", id));
        }
    }


    @Override
    public List<AlarmDto> getAll(String username) {
        return alarmRepository.findAllByUserId(getUserIdByUsername(username))
                .stream()
                .map(alarmMapper::alarmToAlarmDto)
                .collect(Collectors.toList());
    }

    @Override
    public AlarmDto getById(String id, String username) throws NotFoundException {
        return alarmMapper.alarmToAlarmDto(
                alarmRepository.findByIdAndUserId(id, getUserIdByUsername(username))
                        .orElseThrow(() ->
                                new NotFoundException(notFoundMessage("alarm", id))));
    }

    @Override
    public AlarmDto post(AlarmDto alarmDto, String username) {
        UserApp userFromFunction = userRepository.findUserAppByUsername(username).orElseThrow(() -> new
                IllegalArgumentException(notFoundMessage("User", username)));

        if (alarmDto.getId() != null) {
            if (alarmRepository.existsById(alarmDto.getId())) {
                throw new IllegalArgumentException(idExistInDbMessage("Stopwatch", alarmDto.getId()));
            }
            if (!alarmDto.getUserApp().getId().equals(userFromFunction.getId())) {
                throw new IllegalArgumentException(
                        failurePermissionMessage(alarmDto.getUserApp().getId(), username));
            }
        }
        alarmDto.setUserApp(userFromFunction);
        Alarm saveAlarm = alarmRepository.save(alarmMapper.alarmDtoToAlarm(alarmDto));

        return alarmMapper.alarmToAlarmDto(saveAlarm);
    }

    @Override
    public AlarmDto put(AlarmDto alarmDto, String username) {
        UserApp userFromFunction = userRepository.findUserAppByUsername(username).orElseThrow(() -> new
                IllegalArgumentException(notFoundMessage("User", username)));

        if (alarmDto.getId() != null) {
            if (!alarmDto.getUserApp().getId().equals(userFromFunction.getId())) {
                throw new IllegalArgumentException(
                        failurePermissionMessage(alarmDto.getUserApp().getId(), username));
            }
        }

        alarmDto.setUserApp(userFromFunction);
        Alarm saveAlarm = alarmRepository.save(alarmMapper.alarmDtoToAlarm(alarmDto));

        return alarmMapper.alarmToAlarmDto(saveAlarm);
    }

    @Override
    public void delete(String id, String username) throws NotFoundException {
        Alarm alarmToDelete = alarmRepository.findById(id).orElseThrow(() ->
                new NotFoundException(notFoundMessage("alarm", id)));

        if (!alarmToDelete.getUserId().equals(getUserIdByUsername(username))) {
            throw new IllegalArgumentException(
                    failurePermissionMessage(alarmToDelete.getUserId(), username));

        }
        alarmRepository.deleteById(id);
    }


    private String getUserIdByUsername(String username) {
        return userRepository.findUserAppByUsername(username)
                .orElseThrow(() ->
                        new IllegalArgumentException(notFoundMessage("User", username))).getId();
    }
}
