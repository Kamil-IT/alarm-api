package com.clock.clockapi;

import com.clock.clockapi.api.v1.model.Time;
import com.clock.clockapi.security.model.UserApp;
import com.clock.clockapi.api.v1.model.alarm.AlarmTurnOffType;
import com.clock.clockapi.api.v1.model.alarm.frequency.AlarmFrequencyType;
import com.clock.clockapi.api.v1.model.alarm.ring.RingType;
import com.clock.clockapi.api.v1.modeldto.AlarmDto;
import com.clock.clockapi.api.v1.modeldto.StopwatchDto;
import com.clock.clockapi.api.v1.modeldto.TimerDto;
import com.clock.clockapi.services.UserService;
import com.clock.clockapi.services.v1.AlarmService;
import com.clock.clockapi.services.v1.StopwatchService;
import com.clock.clockapi.services.v1.TimerService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.TimeZone;

@Component
public class Bootstrap {

    private final AlarmService alarmService;
    private final StopwatchService stopwatchService;
    private final TimerService timerService;
    private final UserService userService;

    public Bootstrap(AlarmService alarmService, StopwatchService stopwatchService, TimerService timerService, UserService userService) {
        this.alarmService = alarmService;
        this.stopwatchService = stopwatchService;
        this.timerService = timerService;
        this.userService = userService;


//        Init some data

        AlarmDto normalAlarmDto = AlarmDto.builder()
                .name("alarm")
                .description("nice alarm")
                .time(new Time(10, 12, 6, TimeZone.getDefault()))
                .ringType(RingType.BIRDS)
                .alarmFrequencyType(AlarmFrequencyType.MONDAY)
                .userApp(UserApp.builder().id("1234").build())
                .alarmTurnOffType(AlarmTurnOffType.normal)
                .build();

        StopwatchDto stopwatchDto = new StopwatchDto("1234",
                UserApp.builder().id("1234").build(),
                Time.builder().seconds(50).minutes(30).hours(13).build(),
                List.of(
                        Time.builder()
                                .seconds(50).minutes(30).hours(11).build(),
                        Time.builder()
                                .seconds(50).minutes(30).hours(6).build()));

        TimerDto timerDto = TimerDto.builder()
                .userApp(UserApp.builder().id("1234").build())
                .hours(12).minutes(12).seconds(10)
                .currentHours(10).currentMinutes(5).currentSeconds(5)
                .build();


        UserApp user1 = UserApp.builder().username("admin").password("admin").build();
        UserApp user2 = UserApp.builder().username("user").password("user").build();


        alarmService.post(normalAlarmDto);
        stopwatchService.post(stopwatchDto);
        timerService.post(timerDto);
        userService.saveUser(user1);
        userService.saveUser(user2);
    }
}
