package com.clock.clockapi.services.v1;

import com.clock.clockapi.api.v1.mapper.TimerMapper;
import com.clock.clockapi.api.v1.model.stopwatch.Stopwatch;
import com.clock.clockapi.api.v1.model.timer.Timer;
import com.clock.clockapi.api.v1.modeldto.TimerDto;
import com.clock.clockapi.repository.UserRepository;
import com.clock.clockapi.security.model.UserApp;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//@AllArgsConstructor
//@Service
//public class TimerService implements BaseService<TimerDto, String>, BaseUserFilterService<TimerDto, String>{
//
//    private final TimerRepository timerRepository;
//    private final UserRepository userRepository;
//    private final TimerMapper timerMapper;
//
//    @Override
//    public List<TimerDto> getAll() {
//        return timerRepository.findAll()
//                .stream()
//                .map(timerMapper::timerToTimerDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public TimerDto getById(String id) throws NotFoundException {
//        return timerMapper.timerToTimerDto(
//                timerRepository.findById(id)
//                        .orElseThrow(() ->
//                                new NotFoundException(notFoundMessage("Timer", id)))
//        );
//    }
//
//    @Override
//    public TimerDto post(TimerDto timerDto) {
//        if (timerDto.getId() != null) {
//            if (timerRepository.existsById(timerDto.getId())) {
//                throw new IllegalArgumentException(idExistInDbMessage("Timer", timerDto.getId()));
//            }
//        }
//        Timer saveEntity = timerRepository.save(timerMapper.timerDtoToTimer(timerDto));
//        return timerMapper.timerToTimerDto(saveEntity);
//    }
//
//    @Override
//    public TimerDto put(TimerDto timerDto) {
//        Timer saveEntity = timerRepository.save(timerMapper.timerDtoToTimer(timerDto));
//        return timerMapper.timerToTimerDto(saveEntity);
//    }
//
//    @Override
//    public void delete(String id) throws NotFoundException {
//        if (timerRepository.existsById(id)) {
//            timerRepository.deleteById(id);
//        } else {
//            throw new NotFoundException(notFoundMessage("timer", id));
//        }
//    }
//
//    @Override
//    public List<TimerDto> getAll(String username) {
//        return timerRepository.findAllByUserId(getUserIdByUsername(username))
//                .stream()
//                .map(timerMapper::timerToTimerDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public TimerDto getById(String id, String username) throws NotFoundException {
//        return timerMapper.timerToTimerDto(
//                timerRepository.findByIdAndUserId(id, getUserIdByUsername(username))
//                        .orElseThrow(() ->
//                                new NotFoundException(notFoundMessage("Timer", id)))
//        );
//    }
//
//    @Override
//    public TimerDto post(TimerDto timerDto, String username) {
//        UserApp userFromFunction = userRepository.findUserAppByUsername(username).orElseThrow(() -> new
//                IllegalArgumentException(notFoundMessage("User", username)));
//
//        if (timerDto.getId() != null) {
//            if (timerRepository.existsById(timerDto.getId())) {
//                throw new IllegalArgumentException(idExistInDbMessage("Stopwatch", timerDto.getId()));
//            }
//            if (!timerDto.getUserApp().getId().equals(userFromFunction.getId())) {
//                throw new IllegalArgumentException(
//                        failurePermissionMessage(timerDto.getUserApp().getId(), username));
//            }
//        }
//        timerDto.setUserApp(userFromFunction);
//        Timer saveEntity = timerRepository.save(timerMapper.timerDtoToTimer(timerDto));
//        return timerMapper.timerToTimerDto(saveEntity);
//    }
//
//    @Override
//    public TimerDto put(TimerDto timerDto, String username) {
//        UserApp userFromFunction = userRepository.findUserAppByUsername(username).orElseThrow(() -> new
//                IllegalArgumentException(notFoundMessage("User", username)));
//
//        if (timerDto.getId() != null) {
//            if (!timerDto.getUserApp().getId().equals(userFromFunction.getId())) {
//                throw new IllegalArgumentException(
//                        failurePermissionMessage(timerDto.getUserApp().getId(), username));
//            }
//        }
//
//        timerDto.setUserApp(userFromFunction);
//        Timer saveEntity = timerRepository.save(timerMapper.timerDtoToTimer(timerDto));
//        return timerMapper.timerToTimerDto(saveEntity);
//    }
//
//    @Override
//    public void delete(String id, String username) throws NotFoundException {
//        Timer timerToDelete = timerRepository.findById(id).orElseThrow(() ->
//                new NotFoundException(notFoundMessage("alarm", id)));
//
//        if (!timerToDelete.getUserId().equals(getUserIdByUsername(username))) {
//            throw new IllegalArgumentException(
//                    failurePermissionMessage(timerToDelete.getUserId(), username));
//
//        }
//        timerRepository.deleteById(id);
//    }
//
//
//    private String getUserIdByUsername(String username) {
//        return userRepository.findUserAppByUsername(username)
//                .orElseThrow(() ->
//                        new IllegalArgumentException(notFoundMessage("User", username))).getId();
//    }
//}
