package com.clock.clockapi.services.v1;

import com.clock.clockapi.api.v1.mapper.AlarmMapperImpl;
import com.clock.clockapi.api.v1.model.Time;
import com.clock.clockapi.api.v1.model.alarm.Alarm;
import com.clock.clockapi.api.v1.model.alarm.AlarmTurnOffType;
import com.clock.clockapi.api.v1.model.alarm.frequency.AlarmFrequencyType;
import com.clock.clockapi.api.v1.model.alarm.ring.RingType;
import com.clock.clockapi.api.v1.modeldto.AlarmDto;
import com.clock.clockapi.repository.UserRepository;
import com.clock.clockapi.repository.v1.AlarmRepository;
import com.clock.clockapi.security.model.UserApp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlarmServiceTest {

    private static final String USER_ID = UUID.randomUUID().toString();
    private static final UserApp USER_APP = UserApp.builder().build();
    @Mock
    AlarmRepository alarmRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    AlarmMapperImpl alarmMapper;

    @InjectMocks
    AlarmService alarmService;

    Alarm normalAlarm = Alarm.builder()
            .id("5321")
            .name("alarm")
            .description("nice alarm")
            .time("12:12:12")
            .ringType(RingType.BIRDS)
            .alarmFrequencyType(AlarmFrequencyType.MONDAY)
            .userApp(USER_APP)
            .alarmTurnOffType(AlarmTurnOffType.normal)
            .build();

    AlarmDto normalAlarmDto = AlarmDto.builder()
            .id("5321")
            .name("alarm")
            .description("nice alarm")
            .time(new Time(10, 12, 6, TimeZone.getDefault()))
            .ringType(RingType.BIRDS)
            .alarmFrequencyType(AlarmFrequencyType.MONDAY)
            .userId(USER_ID)
            .alarmTurnOffType(AlarmTurnOffType.normal)
            .build();

    @Test
    void post() {
        when(alarmRepository.save(any())).thenReturn(normalAlarm);
        when(alarmMapper.alarmToAlarmDto(any(Alarm.class))).thenReturn(normalAlarmDto);

        AlarmDto returnedAlarm = alarmService.post(normalAlarmDto);

        assertEquals(normalAlarmDto, returnedAlarm);
    }

    @Test
    void post_IdNotNullAndExistInDb() {
        when(alarmRepository.existsById(anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> alarmService.post(normalAlarmDto));
    }

    @Test
    void put() {
        when(alarmRepository.save(any())).thenReturn(normalAlarm);
        when(alarmMapper.alarmToAlarmDto(any())).thenReturn(normalAlarmDto);

        AlarmDto returnedAlarm = alarmService.put(normalAlarmDto);

        assertEquals(normalAlarmDto, returnedAlarm);
    }

    @Test
    void delete() {
//        TODO: Implement it
    }

    @Test
    void testGetAll() {
        //        TODO: Implement it
    }

    @Test
    void testGetById() {
        //        TODO: Implement it
    }

    @Test
    void testPost() {
        //        TODO: Implement it
    }

    @Test
    void testPut() {
        //        TODO: Implement it
    }

    @Test
    void testDelete() {
        //        TODO: Implement it
    }
}