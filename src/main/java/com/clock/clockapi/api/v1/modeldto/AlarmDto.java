package com.clock.clockapi.api.v1.modeldto;

import com.clock.clockapi.api.v1.model.Date;
import com.clock.clockapi.api.v1.model.Time;
import com.clock.clockapi.api.v1.model.alarm.AlarmTurnOffType;
import com.clock.clockapi.api.v1.model.alarm.Snooze;
import com.clock.clockapi.api.v1.model.alarm.frequency.AlarmFrequencyType;
import com.clock.clockapi.api.v1.model.alarm.ring.RingType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Alarm")
@ApiIgnore
public class AlarmDto {

    @ApiModelProperty(position = 2, notes = "Alarm id")
    private String id;

    @ApiModelProperty(position = 4, notes = "Alarm name")
    private String name;

    @ApiModelProperty(position = 5, notes = "Alarm description")
    private String description;

    @ApiModelProperty(position = 7, notes = "User id", accessMode = AccessMode.READ_ONLY)
    private String userId;

    @ApiModelProperty(position = 0, notes = "Alarm time ringing", required = true)
    private Time time;

    @ApiModelProperty(position = 7, notes = "Time when alarm was created or update", accessMode = AccessMode.READ_ONLY)
    private Long timeCreateInMillis;

    @ApiModelProperty(position = 7, notes = "Alarm ringing sound")
    private RingType ringType;

    /**
     * Only not null if ringType = costume
     */
    @ApiModelProperty(position = 8, notes = "Will be available in future")
    private String ringName;

    @ApiModelProperty(position = 3, notes = "Snooze time")
    private Snooze snooze;

    @ApiModelProperty(position = 1, notes = "Day of week when alarm play", required = true)
    private Set<AlarmFrequencyType> alarmFrequencyType;

    /**
     * Only not empty if alarmFrequencyType = costume
     */
    @ApiModelProperty(position = 3, notes = "Day when alarm play in costume date")
    private List<Date> alarmFrequencyCostume;


    @ApiModelProperty(position = 8, notes = "Will be available in future")
    private AlarmTurnOffType alarmTurnOffType;

    @ApiModelProperty(position = 3, notes = "Is alarm active")
    private Boolean isActive;

    @JsonIgnore
    public String getUserId() {
        return userId;
    }

    @JsonIgnore
    public String getRingName() {
        return ringName;
    }
}
