package com.clock.clockapi.api.v1.model.stopwatch;

import com.clock.clockapi.api.v1.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
//@Entity
//@Table(name = "Stopwatchs")
public class Stopwatch extends BaseEntity {

    /**
     * Type: HH:MM:SS
     */
    private String time;

    //    TODO: change to userApp
    private String userId;

    /**
     * Type: HH:MM:SS HH:MM:SS HH:MM:SS HH:MM:SS HH:MM:SS
     * Space separated objects
     */
    private String measuredTimes;

    public Stopwatch(String id, String time, String userId, String measuredTimes) {
        super(id);
        this.time = time;
        this.userId = userId;
        this.measuredTimes = measuredTimes;
    }
}
