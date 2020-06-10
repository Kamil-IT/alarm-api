package com.clock.clockapi.services.v1;

import com.clock.clockapi.api.v1.mapper.AlarmMapper;
import com.clock.clockapi.api.v1.model.alarm.Alarm;
import com.clock.clockapi.api.v1.modeldto.AlarmDto;
import com.clock.clockapi.repository.v1.AlarmRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlarmService implements BaseService<AlarmDto, String> {

    private final AlarmRepository alarmRepository;
    private final AlarmMapper alarmMapper;

    public AlarmService(AlarmRepository alarmRepository, AlarmMapper alarmMapper) {
        this.alarmRepository = alarmRepository;
        this.alarmMapper = alarmMapper;
    }

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
                throw new IllegalArgumentException(idExistInDb("Stopwatch", alarmDto.getId()));
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


}
