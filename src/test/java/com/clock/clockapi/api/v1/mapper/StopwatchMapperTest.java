package com.clock.clockapi.api.v1.mapper;

import com.clock.clockapi.api.v1.model.Time;
import com.clock.clockapi.security.model.UserApp;
import com.clock.clockapi.api.v1.model.stopwatch.Stopwatch;
import com.clock.clockapi.api.v1.modeldto.StopwatchDto;
import com.clock.clockapi.services.UserAppServicesImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {UserAppServicesImpl.class, TimeMapperImpl.class, StopwatchMapperImpl.class})
class StopwatchMapperTest {

    @Autowired
    StopwatchMapper stopwatchMapper;


    StopwatchDto stopwatchDto = new StopwatchDto("1234",
            UserApp.builder().id("1234").build(),
            Time.builder().seconds(50).minutes(30).hours(13).build(),
            List.of(
                    Time.builder()
                            .seconds(50).minutes(30).hours(11).build(),
                    Time.builder()
                            .seconds(50).minutes(30).hours(6).build()
                    ));


    Stopwatch stopwatch = new Stopwatch("1234",
            "12:12:12",
            "1234",
            "10:02:51 02:23:11");

    @Test
    void stopwatchDtoToStopwatch() {

        Stopwatch stopwatch = stopwatchMapper.stopwatchDtoToStopwatch(stopwatchDto);

        String measuredTimesString = stopwatchDto.getMeasuredTimes().get(0) +
                " " +
                stopwatchDto.getMeasuredTimes().get(1) +
                " ";

        assertEquals(stopwatchDto.getId(), stopwatch.getId());
        assertEquals(stopwatchDto.getTime().toString(), stopwatch.getTime());
        assertEquals(stopwatchDto.getUserApp().getId(), stopwatch.getUserId());
        assertEquals(measuredTimesString, stopwatch.getMeasuredTimes());
    }

    @Test
    void stopwatchToStopwatchDto() {

        StopwatchDto stopwatchDto = stopwatchMapper.stopwatchToStopwatchDto(stopwatch);

        String measuredTimesString = stopwatchDto.getMeasuredTimes().get(0) +
                " " +
                stopwatchDto.getMeasuredTimes().get(1) +
                " ";

        assertEquals(stopwatch.getId(), stopwatchDto.getId());
        assertEquals(stopwatch.getTime(), stopwatchDto.getTime().toString());
        assertEquals(stopwatch.getUserId(), stopwatchDto.getUserApp().getId());
        assertEquals(stopwatch.getMeasuredTimes(), measuredTimesString);
    }
}