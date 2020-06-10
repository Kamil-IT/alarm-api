package com.clock.clockapi.api.v1.mapper;

import com.clock.clockapi.api.v1.model.Time;
import com.clock.clockapi.api.v1.model.alarm.Alarm;
import com.clock.clockapi.api.v1.modeldto.AlarmDto;
import com.clock.clockapi.services.UserAppServicesImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UserAppServicesImpl.class, TimeMapper.class})
public interface AlarmMapper {

    @Mapping(target = "userApp", source = "userId", qualifiedByName = "getUserById_UserAppObject")
    @Mapping(target = "time", source = "time", qualifiedByName = "timeStringToTimeObject")
    @Mapping(target = "alarmFrequencyCostume", source = "alarmFrequencyCostume",
            qualifiedByName = "alarmFrequencyCostumeStringToAlarmFrequencyCostumeList")
    AlarmDto alarmToAlarmDto(Alarm alarm);

    @Mapping(target = "userId", source = "userApp.id")
    @Mapping(target = "time", source = "time", qualifiedByName = "timeObjectToTimeString")
    @Mapping(target = "alarmFrequencyCostume", source = "alarmFrequencyCostume",
            qualifiedByName = "alarmFrequencyCostumeListToAlarmFrequencyCostumeString")
    Alarm alarmDtoToAlarm(AlarmDto alarmDto);

    @Named("alarmFrequencyCostumeStringToAlarmFrequencyCostumeList")
    default List<Time> alarmFrequencyCostumeStringToAlarmFrequencyCostumeList(String alarmFrequencyCostume){
//        TODO: implement this
        return new ArrayList<>();
    }
    @Named("alarmFrequencyCostumeListToAlarmFrequencyCostumeString")
    default String alarmFrequencyCostumeListToAlarmFrequencyCostumeString(List<Time> alarmFrequencyCostume){
        if (alarmFrequencyCostume == null){
            return "";
        }
        StringBuilder responseBuilder = new StringBuilder();
        for (Time time :
                alarmFrequencyCostume) {
            responseBuilder.append(time.toString()).append(" ");
        }
        return responseBuilder.toString();
    }
}
