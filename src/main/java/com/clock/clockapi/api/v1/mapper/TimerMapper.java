package com.clock.clockapi.api.v1.mapper;

import com.clock.clockapi.api.v1.model.timer.Timer;
import com.clock.clockapi.api.v1.modeldto.TimerDto;

import com.clock.clockapi.services.UserServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserServiceImpl.class)
public interface TimerMapper {

    @Mapping(target = "userId", source = "userApp.id")
    Timer timerDtoToTimer(TimerDto timerDto);

    @Mapping(target = "userApp", source = "userId", qualifiedByName = "getUserById_UserAppObject")
    TimerDto timerToTimerDto(Timer timer);
}
