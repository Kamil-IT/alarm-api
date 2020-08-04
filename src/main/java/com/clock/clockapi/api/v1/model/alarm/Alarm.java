package com.clock.clockapi.api.v1.model.alarm;

import com.clock.clockapi.api.v1.conventer.AlarmTurnOffTypeSetConverter;
import com.clock.clockapi.api.v1.model.BaseEntity;
import com.clock.clockapi.api.v1.model.alarm.frequency.AlarmFrequencyType;
import com.clock.clockapi.api.v1.model.alarm.ring.RingType;
import com.clock.clockapi.security.model.UserApp;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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
    private Snooze snooze;

    @Convert(converter = AlarmTurnOffTypeSetConverter.class)
    private Set<AlarmFrequencyType> alarmFrequencyType;

    /**
     * Only not null if alarmFrequencyType = costume
     * Type DD:MM:YYYY DD:MM:YYYY DD:MM:YYYY
     */
    private String alarmFrequencyCostume;


    @Enumerated(EnumType.STRING)
    private AlarmTurnOffType alarmTurnOffType;

    private Long timeCreateInMillis;

    private Boolean isActive;

    @PrePersist
    public void ringTypeCostume_alarmFrequencyCostume(){
//        ringTypeCostume_alarmFrequencyCostume
        if (ringType == RingType.COSTUME && ringName == null){
            throw new IllegalArgumentException("If RingType != COSTUME, ringName have to not be null");
        }
        else {
            ringName = null;
        }
        if (alarmFrequencyType.contains(AlarmFrequencyType.CUSTOM) && alarmFrequencyCostume == null){
            throw new IllegalArgumentException("If AlarmFrequencyType haven't got in list COSTUME, alarmFrequencyType have to not be null");
        }
        else {
            alarmFrequencyCostume = null;
        }

//        TimeCreate is correct
        if (timeCreateInMillis == null){
            timeCreateInMillis = System.currentTimeMillis();
        }

//        Is active
        if (isActive == null){
            isActive = false;
        }
    }

}
