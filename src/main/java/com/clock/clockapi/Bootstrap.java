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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.TimeZone;

@Slf4j
@Component
public class Bootstrap {

    private final AlarmService alarmService;
    private final StopwatchService stopwatchService;
    private final TimerService timerService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public Bootstrap(AlarmService alarmService, StopwatchService stopwatchService, TimerService timerService, UserService userService, PasswordEncoder passwordEncoder) {
        this.alarmService = alarmService;
        this.stopwatchService = stopwatchService;
        this.timerService = timerService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;


//        Init some data

        UserApp user1 = UserApp.builder()
                .username("admin")
                .password("admin").build();
        UserApp user2 = UserApp.builder()
                .username("user")
                .password("user").build();

        user1 = userService.saveUser(user1);
        user2 = userService.saveUser(user2);

        AlarmDto normalAlarmDto = AlarmDto.builder()
                .name("alarm")
                .description("nice alarm")
                .time(new Time(10, 12, 6, TimeZone.getDefault()))
                .ringType(RingType.BIRDS)
                .alarmFrequencyType(AlarmFrequencyType.MONDAY)
                .userId(user1.getId())
                .alarmTurnOffType(AlarmTurnOffType.normal)
                .build();

        StopwatchDto stopwatchDto = new StopwatchDto("1234",
                user2,
                Time.builder().seconds(50).minutes(30).hours(13).build(),
                List.of(
                        Time.builder()
                                .seconds(50).minutes(30).hours(11).build(),
                        Time.builder()
                                .seconds(50).minutes(30).hours(6).build()));

        TimerDto timerDto = TimerDto.builder()
                .userApp(user1)
                .hours(12).minutes(12).seconds(10)
                .currentHours(10).currentMinutes(5).currentSeconds(5)
                .build();


        alarmService.post(normalAlarmDto);
        stopwatchService.post(stopwatchDto);
        timerService.post(timerDto);
    }
}
