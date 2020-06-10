package com.clock.clockapi.services.v1;

import com.clock.clockapi.api.v1.mapper.StopwatchMapper;
import com.clock.clockapi.api.v1.model.stopwatch.Stopwatch;
import com.clock.clockapi.api.v1.modeldto.StopwatchDto;
import com.clock.clockapi.repository.v1.StopwatchRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StopwatchService implements BaseService<StopwatchDto, String> {

    private final StopwatchRepository stopwatchRepository;
    private final StopwatchMapper stopwatchMapper;

    public StopwatchService(StopwatchRepository stopwatchRepository, StopwatchMapper stopwatchMapper) {
        this.stopwatchRepository = stopwatchRepository;
        this.stopwatchMapper = stopwatchMapper;
    }

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
                throw new IllegalArgumentException(idExistInDb("Stopwatch", stopwatchDto.getId()));
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
}
