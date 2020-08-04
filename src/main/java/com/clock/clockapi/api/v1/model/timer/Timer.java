package com.clock.clockapi.api.v1.model.timer;

import com.clock.clockapi.api.v1.model.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "Timers")
public class Timer extends BaseEntity {

    public static final int HOUR_MAX = 12;
    public static final int HOUR_MIN = 0;
    public static final int MINUTE_MAX = 60;
    public static final int MINUTE_MIN = 0;
    public static final int SECOND_MAX = 60;
    public static final int SECOND_MIN = 0;

//    TODO: change to userApp
    private String userId;

    private Integer hours = 0;
    private Integer minutes = 0;
    private Integer seconds = 0;

    @Column(name = "current_hours")
    private Integer currentHours = 0;

    @Column(name = "current_minutes")
    private Integer currentMinutes = 0;

    @Column(name = "current_seconds")
    private Integer currentSeconds = 0;

    public void setHours(Integer hours) {
        if (hours > HOUR_MAX || hours < HOUR_MIN){
            throw new IllegalArgumentException("Hours have to be in range "+ HOUR_MIN + "-" + HOUR_MAX);
        }
        this.hours = hours;
    }

    public void setMinutes(Integer minutes) {
        if (minutes > MINUTE_MAX || minutes < MINUTE_MIN){
            throw new IllegalArgumentException("Minutes have to be in range "+ MINUTE_MIN + "-" + MINUTE_MAX);
        }
        this.minutes = minutes;
    }

    public void setSeconds(Integer seconds) {
        if (seconds > SECOND_MAX || seconds < SECOND_MIN){
            throw new IllegalArgumentException("Seconds have to be in range "+ SECOND_MIN + "-" + SECOND_MAX);
        }
        this.seconds = seconds;
    }


    public void setCurrentHours(Integer hours) {
        if (hours > HOUR_MAX || hours < HOUR_MIN){
            throw new IllegalArgumentException("Hours have to be in range "+ HOUR_MIN + "-" + HOUR_MAX);
        }
        this.currentHours = hours;
    }

    public void setCurrentMinutes(Integer minutes) {
        if (minutes > MINUTE_MAX || minutes < MINUTE_MIN){
            throw new IllegalArgumentException("Minutes have to be in range "+ MINUTE_MIN + "-" + MINUTE_MAX);
        }
        this.currentMinutes = minutes;
    }

    public void setCurrentSeconds(Integer seconds) {
        if (seconds > SECOND_MAX || seconds < SECOND_MIN){
            throw new IllegalArgumentException("Seconds have to be in range "+ SECOND_MIN + "-" + SECOND_MAX);
        }
        this.currentSeconds = seconds;
    }

}
