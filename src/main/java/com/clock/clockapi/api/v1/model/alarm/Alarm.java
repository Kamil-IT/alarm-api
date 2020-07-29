package com.clock.clockapi.api.v1.model.alarm;

import com.clock.clockapi.api.v1.model.BaseEntity;
import com.clock.clockapi.api.v1.model.alarm.frequency.AlarmFrequencyType;
import com.clock.clockapi.api.v1.model.alarm.ring.RingType;
import com.clock.clockapi.security.model.UserApp;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

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
     * TODO: UTC...
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
    private AlarmFrequencyType alarmFrequencyType;

    /**
     * Only not null if alarmFrequencyType = costume
     * Type DD:MM:YYYY DD:MM:YYYY DD:MM:YYYY
     */
    private String alarmFrequencyCostume;

    @Enumerated(EnumType.STRING)
    private AlarmTurnOffType alarmTurnOffType;


    @PrePersist
    public void ringTypeCostume_alarmFrequencyCostume(){
        if (ringType == RingType.COSTUME && ringName == null){
            throw new IllegalArgumentException("If RingType != COSTUME, ringName have to not be null");
        }
        else {
            ringName = null;
        }
        if (alarmFrequencyType == AlarmFrequencyType.CUSTOM && alarmFrequencyCostume == null){
            throw new IllegalArgumentException("If AlarmFrequencyType != COSTUME, alarmFrequencyType have to not be null");
        }
        else {
            alarmFrequencyCostume = null;
        }
    }

}
