package com.clock.clockapi.api.v1.modeldto;

import com.clock.clockapi.api.v1.model.Date;
import com.clock.clockapi.api.v1.model.Time;
import com.clock.clockapi.security.model.UserApp;
import com.clock.clockapi.api.v1.model.alarm.AlarmTurnOffType;
import com.clock.clockapi.api.v1.model.alarm.frequency.AlarmFrequencyType;
import com.clock.clockapi.api.v1.model.alarm.ring.RingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlarmDto {

    private String id;

    private String name;
    private String description;

    private String userId;

    private Time time;

    private RingType ringType;

    /**
     * Only not null if ringType = costume
     */
    private String ringName;


    private AlarmFrequencyType alarmFrequencyType;

    /**
     * Only not empty if alarmFrequencyType = costume
     */
    private List<Date> alarmFrequencyCostume;


    private AlarmTurnOffType alarmTurnOffType;
}
