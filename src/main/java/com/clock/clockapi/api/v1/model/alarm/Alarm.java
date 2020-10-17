package com.clock.clockapi.api.v1.model.alarm;

import com.clock.clockapi.api.v1.conventer.AlarmFrequencyCostumeListConverter;
import com.clock.clockapi.api.v1.conventer.AlarmTurnOffTypeSetConverter;
import com.clock.clockapi.api.v1.model.BaseEntity;
import com.clock.clockapi.api.v1.model.Date;
import com.clock.clockapi.api.v1.model.alarm.frequency.AlarmFrequencyType;
import com.clock.clockapi.api.v1.model.alarm.ring.RingType;
import com.clock.clockapi.security.model.UserApp;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "Alarms")
public class Alarm extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserApp userApp;

    private String name;
    private String description;

    /**
     * Type: HH:MM:SS
     */
    private String time;

    @Enumerated(EnumType.STRING)
    private RingType ringType;

    /**
     * Only not null if ringType = costume
     */
    private String ringName;

    @Enumerated(EnumType.STRING)
    private Snooze snooze;

    @Convert(converter = AlarmTurnOffTypeSetConverter.class)
    private Set<AlarmFrequencyType> alarmFrequencyType;

    /**
     * Only not empty if alarmFrequencyType = costume or single
     */
    @Convert(converter = AlarmFrequencyCostumeListConverter.class)
    private List<Date> alarmFrequencyCostume;


    @Enumerated(EnumType.STRING)
    private AlarmTurnOffType alarmTurnOffType;

    private Long timeCreateInMillis;

    private Boolean isActive;

    @PrePersist
    public void ringTypeCostume_alarmFrequencyCostume() {
//          It will be use full after add possibility to add costume alarm
//        ringTypeCostume_alarmFrequencyCostume
//        if (ringType == RingType.COSTUME && ringName == null){
//            throw new IllegalArgumentException("If RingType != COSTUME, ringName have to not be null");
//        }
//        else {
//            ringName = null;
//        }
//        alarmFrequencyType and alarmFrequencyCostume is correct
        if (ringType == RingType.COSTUME) {
            ringType = RingType.ALARM_CLASSIC;
            ringName = null;
        }
        if (alarmFrequencyType.contains(AlarmFrequencyType.CUSTOM)) {
//            TODO: here might be error "calendar.get(Calendar.MONTH)"
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new java.util.Date());
            if (alarmFrequencyCostume == null) {
                alarmFrequencyCostume = List.of(new com.clock.clockapi.api.v1.model.Date(
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.YEAR)));
            }
        } else if (alarmFrequencyType.isEmpty()) {
//            TODO: here might be error "calendar.get(Calendar.MONTH)"
            alarmFrequencyType.add(AlarmFrequencyType.CUSTOM);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new java.util.Date());
            this.alarmFrequencyCostume = List.of(new com.clock.clockapi.api.v1.model.Date(
                    calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.YEAR)));
        }

//        TimeCreate is correct
        if (timeCreateInMillis == null) {
            timeCreateInMillis = System.currentTimeMillis();
        }

//        Is active
        if (isActive == null) {
            isActive = false;
        }
//        Snooze
        if (snooze == null) {
            snooze = Snooze.MIN_5;
        }
    }

}
