package com.clock.clockapi.api.v1.mapper;

import com.clock.clockapi.security.model.UserApp;
import com.clock.clockapi.api.v1.model.timer.Timer;
import com.clock.clockapi.api.v1.modeldto.TimerDto;
import com.clock.clockapi.services.UserAppServicesImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {TimerMapperImpl.class, UserAppServicesImpl.class})
class TimerMapperTest {

    @Autowired
    TimerMapper timerMapper;

    Timer timer = Timer.builder()
            .id("4321")
            .userId("1234")
            .hours(12).minutes(12).seconds(10)
            .currentHours(10).currentMinutes(5).currentSeconds(5)
            .build();

    TimerDto timerDto = TimerDto.builder()
            .id("4321")
            .userApp(UserApp.builder().id("1234").build())
            .hours(12).minutes(12).seconds(10)
            .currentHours(10).currentMinutes(5).currentSeconds(5)
            .build();

    @Test
    void timerDtoToTimer() {

        Timer timer = timerMapper.timerDtoToTimer(timerDto);

        assertEquals(timerDto.getId(), timer.getId());
        assertEquals(timerDto.getCurrentHours(), timer.getCurrentHours());
        assertEquals(timerDto.getCurrentMinutes(), timer.getCurrentMinutes());
        assertEquals(timerDto.getCurrentSeconds(), timer.getCurrentSeconds());
        assertEquals(timerDto.getHours(), timer.getHours());
        assertEquals(timerDto.getMinutes(), timer.getMinutes());
        assertEquals(timerDto.getSeconds(), timer.getSeconds());
        assertEquals(timerDto.getUserApp().getId(), timer.getUserId());
    }

    @Test
    void timerToTimerDro() {
        TimerDto timerDto = timerMapper.timerToTimerDto(timer);

        assertEquals(timerDto.getId(), timer.getId());
        assertEquals(timerDto.getCurrentHours(), timer.getCurrentHours());
        assertEquals(timerDto.getCurrentMinutes(), timer.getCurrentMinutes());
        assertEquals(timerDto.getCurrentSeconds(), timer.getCurrentSeconds());
        assertEquals(timerDto.getHours(), timer.getHours());
        assertEquals(timerDto.getMinutes(), timer.getMinutes());
        assertEquals(timerDto.getSeconds(), timer.getSeconds());
        assertEquals(timerDto.getUserApp().getId(), timer.getUserId());
    }
}