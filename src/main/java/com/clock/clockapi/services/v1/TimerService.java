package com.clock.clockapi.services.v1;

import com.clock.clockapi.api.v1.mapper.TimerMapper;
import com.clock.clockapi.api.v1.model.timer.Timer;
import com.clock.clockapi.api.v1.modeldto.TimerDto;
import com.clock.clockapi.repository.v1.TimerRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimerService implements BaseService<TimerDto, String>{

    private final TimerRepository timerRepository;
    private final TimerMapper timerMapper;

    public TimerService(TimerRepository timerRepository, TimerMapper timerMapper) {
        this.timerRepository = timerRepository;
        this.timerMapper = timerMapper;
    }

    @Override
    public List<TimerDto> getAll() {
        return timerRepository.findAll()
                .stream()
                .map(timerMapper::timerToTimerDto)
                .collect(Collectors.toList());
    }

    @Override
    public TimerDto getById(String id) throws NotFoundException {
        return timerMapper.timerToTimerDto(
                timerRepository.findById(id)
                        .orElseThrow(() ->
                                new NotFoundException(notFoundMessage("Timer", id)))
        );
    }

    @Override
    public TimerDto post(TimerDto timerDto) {
        if (timerDto.getId() != null) {
            if (timerRepository.existsById(timerDto.getId())) {
                throw new IllegalArgumentException(idExistInDb("Timer", timerDto.getId()));
            }
        }
        Timer saveEntity = timerRepository.save(timerMapper.timerDtoToTimer(timerDto));
        return timerMapper.timerToTimerDto(saveEntity);
    }

    @Override
    public TimerDto put(TimerDto timerDto) {
        Timer saveEntity = timerRepository.save(timerMapper.timerDtoToTimer(timerDto));
        return timerMapper.timerToTimerDto(saveEntity);
    }

    @Override
    public void delete(String s) throws NotFoundException {

    }
}
