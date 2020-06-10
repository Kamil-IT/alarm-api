package com.clock.clockapi.api.v1.mapper;

import com.clock.clockapi.api.v1.model.Time;
import org.mapstruct.Mapper;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface TimeMapper {

    @Named("timeStringToTimeObject")
    default com.clock.clockapi.api.v1.model.Time timeStringToTimeObject(String time){
        return new Time(time);
    }

    @Named("timeObjectToTimeString")
    default String timeObjectToTimeString(Time time){
        return time.toString();
    }
}
