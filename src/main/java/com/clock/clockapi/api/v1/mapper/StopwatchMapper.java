package com.clock.clockapi.api.v1.mapper;


import com.clock.clockapi.api.v1.model.Time;
import com.clock.clockapi.api.v1.model.stopwatch.Stopwatch;
import com.clock.clockapi.api.v1.modeldto.StopwatchDto;

import com.clock.clockapi.services.UserServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UserServiceImpl.class, TimeMapper.class})
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
    default List<Time> measuredTimesStringToMeasuredTimesList(String measuredTimes){
        ArrayList<Time> times = new ArrayList<>();

        if (measuredTimes == null){
            return times;
        }

        for (int i = 0; i < measuredTimes.length(); i = i + 9) {
            Time time = new Time();
            time.setHours(Integer.parseInt(measuredTimes.substring(i + 0, i + 2)));
            time.setMinutes(Integer.parseInt(measuredTimes.substring(i + 3, i + 5)));
            time.setSeconds(Integer.parseInt(measuredTimes.substring(i + 6, i + 8)));

            times.add(time);
        }

        return times;
    }
}
