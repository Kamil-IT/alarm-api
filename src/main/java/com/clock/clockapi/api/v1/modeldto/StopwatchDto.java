package com.clock.clockapi.api.v1.modeldto;

import com.clock.clockapi.api.v1.model.Time;
import com.clock.clockapi.security.model.UserApp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StopwatchDto {

    private String id;

    private UserApp userApp;

    private Time time;
    private List<Time> measuredTimes = new ArrayList<>();
}
