package com.clock.clockapi.api.v1.mapper;


import com.clock.clockapi.api.v1.model.Time;
import com.clock.clockapi.api.v1.model.stopwatch.Stopwatch;
import com.clock.clockapi.api.v1.modeldto.StopwatchDto;

import com.clock.clockapi.services.UserAppServicesImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UserAppServicesImpl.class, TimeMapper.class})
public interface StopwatchMapper {

    @Mapping(target = "userId", source = "userApp.id")
    @Mapping(target = "time", source = "time", qualifiedByName = "timeObjectToTimeString")
    @Mapping(target = "measuredTimes", source = "measuredTimes",
            qualifiedByName = "measuredTimesListToMeasuredTimesString")
    Stopwatch stopwatchDtoToStopwatch(StopwatchDto stopwatchDto);

    @Mapping(target = "userApp", source = "userId", qualifiedByName = "getUserById_UserAppObject")
    @Mapping(target = "time", source = "time", qualifiedByName = "timeStringToTimeObject")
    @Mapping(target = "measuredTimes", source = "measuredTimes",
            qualifiedByName = "measuredTimesStringToMeasuredTimesList")
    StopwatchDto stopwatchToStopwatchDto(Stopwatch stopwatch);

    @Named("measuredTimesListToMeasuredTimesString")
    default String measuredTimesListToMeasuredTimesString(List<Time> measuredTimes){
        StringBuilder responseBuilder = new StringBuilder();
        for (Time time :
                measuredTimes) {
            responseBuilder.append(time.toString()).append(" ");
        }
        return responseBuilder.toString();
    }

    @Named("measuredTimesStringToMeasuredTimesList")
    default List<Time> measuredTimesListToMeasuredTimesString(String measuredTimes){
//        TODO: implement this
        return new ArrayList<>();
    }
}
