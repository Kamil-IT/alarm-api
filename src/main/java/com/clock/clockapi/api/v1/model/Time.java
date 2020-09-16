package com.clock.clockapi.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.TimeZone;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class Time extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @JsonIgnore
    private String id;

    public static final int HOUR_MAX = 12;
    public static final int HOUR_MIN = 0;
    public static final int MINUTE_MAX = 60;
    public static final int MINUTE_MIN = 0;
    public static final int SECOND_MAX = 60;
    public static final int SECOND_MIN = 0;

    private Integer hours;
    private Integer minutes;
    private Integer seconds;

    @JsonIgnore
    private TimeZone timeZone;

    public Time(String timeInString){
        hours = Integer.parseInt(timeInString.substring(0, 2));
        minutes = Integer.parseInt(timeInString.substring(3, 5));
        seconds = Integer.parseInt(timeInString.substring(6));
    }

    public Time(Integer hours, Integer minutes, Integer seconds, TimeZone timeZone) {
        if (hours > HOUR_MAX || hours < HOUR_MIN ||
                minutes > MINUTE_MAX || minutes < MINUTE_MIN ||
                seconds > SECOND_MAX || seconds < SECOND_MIN){
            throw new IllegalArgumentException("Some validation error...");
        }
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.timeZone = timeZone;
    }



//    public static TimeBuilder builder() {
//        return new TimeBuilder();
//    }
//
//    public static class TimeBuilder {
//        private Integer hours;
//        private Integer minutes;
//        private Integer seconds;
//        private TimeZone timeZone;
//
//        TimeBuilder() {
//        }
//
//        public TimeBuilder hours(Integer hours) {
//            if (hours > HOUR_MAX || hours < HOUR_MIN){
//                throw new IllegalArgumentException("Hours have to be in range "+ HOUR_MIN + "-" + HOUR_MAX);
//            }
//            this.hours = hours;
//            return this;
//        }
//
//        public TimeBuilder minutes(Integer minutes) {
//            if (minutes > MINUTE_MAX || minutes < MINUTE_MIN){
//                throw new IllegalArgumentException("Minutes have to be in range "+ MINUTE_MIN + "-" + MINUTE_MAX);
//            }
//            this.minutes = minutes;
//            return this;
//        }
//
//        public TimeBuilder seconds(Integer seconds) {
//            if (seconds > SECOND_MAX || seconds < SECOND_MIN){
//                throw new IllegalArgumentException("Seconds have to be in range "+ SECOND_MIN + "-" + SECOND_MAX);
//            }
//            this.seconds = seconds;
//            return this;
//        }
//
//        public TimeBuilder timeZone(TimeZone timeZone) {
//            this.timeZone = timeZone;
//            return this;
//        }
//
//        public Time build() {
//            return new Time(hours, minutes, seconds, timeZone);
//        }
//
//        public String toString() {
//            return "Time.TimeBuilder(hours=" + this.hours + ", minutes=" + this.minutes + ", seconds=" + this.seconds + ", timeZone=" + this.timeZone + ")";
//        }
//    }
//
//    public void setHours(Integer hours) {
//        if (hours > HOUR_MAX || hours < HOUR_MIN){
//            throw new IllegalArgumentException("Hours have to be in range "+ HOUR_MIN + "-" + HOUR_MAX);
//        }
//        this.hours = hours;
//    }
//
//    public void setMinutes(Integer minutes) {
//        if (minutes > MINUTE_MAX || minutes < MINUTE_MIN){
//            throw new IllegalArgumentException("Minutes have to be in range "+ MINUTE_MIN + "-" + MINUTE_MAX);
//        }
//        this.minutes = minutes;
//    }
//
//    public void setSeconds(Integer seconds) {
//        if (seconds > SECOND_MAX || seconds < SECOND_MIN){
//            throw new IllegalArgumentException("Seconds have to be in range "+ SECOND_MIN + "-" + SECOND_MAX);
//        }
//        this.seconds = seconds;
//    }

    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder();
        if (hours < 10){
            returnString.append("0").append(hours);
        }
        else {
            returnString.append(hours);
        }
        returnString.append(":");
        if (minutes < 10){
            returnString.append("0").append(minutes);
        }
        else {
            returnString.append(minutes);
        }
        returnString.append(":");
        if (seconds < 10){
            returnString.append("0").append(seconds);
        }
        else {
            returnString.append(seconds);
        }

        return returnString.toString();
    }
}
