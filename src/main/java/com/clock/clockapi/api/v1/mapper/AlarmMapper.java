package com.clock.clockapi.api.v1.mapper;

import com.clock.clockapi.api.v1.model.Date;
import com.clock.clockapi.api.v1.model.alarm.Alarm;
import com.clock.clockapi.api.v1.modeldto.AlarmDto;
import com.clock.clockapi.services.UserServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UserServiceImpl.class, TimeMapper.class})
public interface AlarmMapper {

    @Mapping(target = "userId", source = "userApp.id")
    @Mapping(target = "time", source = "time", qualifiedByName = "timeStringToTimeObject")
    AlarmDto alarmToAlarmDto(Alarm alarm);

    @Mapping(target = "userApp", source = "userId", qualifiedByName = "getUserById")
    @Mapping(target = "time", source = "time", qualifiedByName = "timeObjectToTimeString")
    Alarm alarmDtoToAlarm(AlarmDto alarmDto);

//    @Named("alarmFrequencyCostumeStringToAlarmFrequencyCostumeList")
//    default List<Date> alarmFrequencyCostumeStringToAlarmFrequencyCostumeList(String alarmFrequencyCostume){
//        ArrayList<Date> times = new ArrayList<>();
//        if (alarmFrequencyCostume == null){
//            return times;
//        }
//        for (int i = 0; i < alarmFrequencyCostume.length(); i = i + 11) {
//            Date time = new Date();
//            time.setDay(Integer.parseInt(alarmFrequencyCostume.substring(i + 0, i + 2)));
//            time.setMonth(Integer.parseInt(alarmFrequencyCostume.substring(i + 3, i + 5)));
//            time.setYear(Integer.parseInt(alarmFrequencyCostume.substring(i + 6, i + 10)));
//
//            times.add(time);
//        }
//
//        return times;
//    }
//    @Named("alarmFrequencyCostumeListToAlarmFrequencyCostumeString")
//    default String alarmFrequencyCostumeListToAlarmFrequencyCostumeString(List<Date> alarmFrequencyCostume){
//        if (alarmFrequencyCostume == null){
//            return "";
//        }
//        StringBuilder responseBuilder = new StringBuilder();
//        for (Date date :
//                alarmFrequencyCostume) {
//            responseBuilder.append(date.toString()).append(" ");
//        }
//        return responseBuilder.toString();
//    }
}
