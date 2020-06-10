package com.clock.clockapi.api.v1.modeldto;

import com.clock.clockapi.security.model.UserApp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TimerDto {

    private String id;

    private UserApp userApp;

    private Integer hours;
    private Integer minutes;
    private Integer seconds;

    private Integer currentHours;
    private Integer currentMinutes;
    private Integer currentSeconds;
}
