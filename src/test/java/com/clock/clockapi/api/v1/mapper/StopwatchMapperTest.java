package com.clock.clockapi.api.v1.mapper;

import com.clock.clockapi.api.v1.model.Time;
import com.clock.clockapi.repository.UserRepository;
import com.clock.clockapi.security.model.UserApp;
import com.clock.clockapi.api.v1.model.stopwatch.Stopwatch;
import com.clock.clockapi.api.v1.modeldto.StopwatchDto;
import com.clock.clockapi.services.UserServiceImpl;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {TimeMapperImpl.class, StopwatchMapperImpl.class})
class StopwatchMapperTest {

    public static final UserApp USER_APP = UserApp.builder().id("1234").build();
    @Autowired
    StopwatchMapper stopwatchMapper;

    @MockBean
    UserServiceImpl userService;

    StopwatchDto stopwatchDto = new StopwatchDto("1234",
            USER_APP,
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
    void stopwatchToStopwatchDto() throws NotFoundException {

        when(userService.getUserById_UserAppObject(any(UserApp.class))).thenReturn(USER_APP);
        when(userService.getUserById(anyString())).thenReturn(USER_APP);

        StopwatchDto stopwatchDto = stopwatchMapper.stopwatchToStopwatchDto(stopwatch);

        String measuredTimesString = stopwatchDto.getMeasuredTimes().get(0) +
                " " +
                stopwatchDto.getMeasuredTimes().get(1);

        assertEquals(stopwatch.getId(), stopwatchDto.getId());
        assertEquals(stopwatch.getTime(), stopwatchDto.getTime().toString());
        assertEquals(stopwatch.getUserId(), stopwatchDto.getUserApp().getId());
        assertEquals(stopwatch.getMeasuredTimes(), measuredTimesString);
    }

    @Test
    void measuredTimesStringToMeasuredTimesList() {
        List<Time> times = stopwatchMapper.measuredTimesStringToMeasuredTimesList("12:09:12 10:11:20");

        assertEquals(12, times.get(0).getHours());
        assertEquals(9, times.get(0).getMinutes());
        assertEquals(12, times.get(0).getSeconds());
        assertEquals(10, times.get(1).getHours());
        assertEquals(11, times.get(1).getMinutes());
        assertEquals(20, times.get(1).getSeconds());

    }

    @Test
    void measuredTimesStringToMeasuredTimesList_null() {
        List<Time> times = stopwatchMapper.measuredTimesStringToMeasuredTimesList(null);

        assertEquals(0, times.size());

    }
}