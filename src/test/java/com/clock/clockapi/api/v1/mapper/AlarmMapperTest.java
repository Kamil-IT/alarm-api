package com.clock.clockapi.api.v1.mapper;

import com.clock.clockapi.api.v1.model.Time;
import com.clock.clockapi.security.model.UserApp;
import com.clock.clockapi.api.v1.model.alarm.Alarm;
import com.clock.clockapi.api.v1.model.alarm.AlarmTurnOffType;
import com.clock.clockapi.api.v1.model.alarm.frequency.AlarmFrequencyType;
import com.clock.clockapi.api.v1.model.alarm.ring.RingType;
import com.clock.clockapi.api.v1.modeldto.AlarmDto;
import com.clock.clockapi.services.UserAppServicesImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {UserAppServicesImpl.class, TimeMapperImpl.class, AlarmMapperImpl.class})
class AlarmMapperTest {

    public static final String USER_ID = "1234";
    public static final UserApp USER_APP = UserApp.builder().id(USER_ID).build();

    @Autowired
    AlarmMapper alarmMapper;

    @Autowired
    UserAppServicesImpl userAppServices;

    Alarm normalAlarm = Alarm.builder()
            .id("5321")
            .name("alarm")
            .description("nice alarm")
            .time("12:12:12")
            .ringType(RingType.BIRDS)
            .alarmFrequencyType(AlarmFrequencyType.MONDAY)
            .userId(USER_ID)
            .alarmTurnOffType(AlarmTurnOffType.normal)
            .build();

    Alarm costumeAlarm = Alarm.builder()
            .id("5321")
            .name("alarm")
            .description("nice alarm")
            .time("12:12:12")
            .ringType(RingType.COSTUME)
            .ringName("house")
            .alarmFrequencyType(AlarmFrequencyType.CUSTOM)
            .alarmFrequencyCostume("22-11-2020")
            .userId("1234")
            .alarmTurnOffType(AlarmTurnOffType.normal)
            .build();

    AlarmDto normalAlarmDto = AlarmDto.builder()
            .id("5321")
            .name("alarm")
            .description("nice alarm")
            .time(new Time(10, 12, 6, TimeZone.getDefault()))
            .ringType(RingType.BIRDS)
            .alarmFrequencyType(AlarmFrequencyType.MONDAY)
            .userApp(USER_APP)
            .alarmTurnOffType(AlarmTurnOffType.normal)
            .build();

    @Test
    void alarmToAlarmDto() {

        AlarmDto alarmDto = alarmMapper.alarmToAlarmDto(normalAlarm);

        assertEquals(normalAlarm.getId(), alarmDto.getId());
        assertEquals(normalAlarm.getName(), alarmDto.getName());
        assertEquals(normalAlarm.getDescription(), alarmDto.getDescription());
        assertEquals(normalAlarm.getTime(), alarmDto.getTime().toString());
        assertEquals(normalAlarm.getRingType(), alarmDto.getRingType());
        assertEquals(normalAlarm.getAlarmFrequencyType(), alarmDto.getAlarmFrequencyType());
        assertEquals(normalAlarm.getUserId(), alarmDto.getUserApp().getId());
        assertEquals(normalAlarm.getAlarmTurnOffType(), alarmDto.getAlarmTurnOffType());
    }

    @Test
    void alarmDtoToAlarm() {

        Alarm alarm = alarmMapper.alarmDtoToAlarm(normalAlarmDto);

        assertEquals(normalAlarmDto.getId(), alarm.getId());
        assertEquals(normalAlarmDto.getName(), alarm.getName());
        assertEquals(normalAlarmDto.getDescription(), alarm.getDescription());
        assertEquals(normalAlarmDto.getTime().toString(), alarm.getTime());
        assertEquals(normalAlarmDto.getRingType(), alarm.getRingType());
        assertEquals(normalAlarmDto.getAlarmFrequencyType(), alarm.getAlarmFrequencyType());
        assertEquals(normalAlarmDto.getUserApp().getId(), alarm.getUserId());
        assertEquals(normalAlarmDto.getAlarmTurnOffType(), alarm.getAlarmTurnOffType());
    }

//    TODO: add tests to costume alarms
}