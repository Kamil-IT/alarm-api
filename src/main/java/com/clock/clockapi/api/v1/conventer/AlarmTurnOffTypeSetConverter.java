package com.clock.clockapi.api.v1.conventer;

import com.clock.clockapi.api.v1.model.alarm.AlarmTurnOffType;
import com.clock.clockapi.api.v1.model.alarm.frequency.AlarmFrequencyType;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.util.HashSet;
import java.util.Set;

@Component
@Convert
public class AlarmTurnOffTypeSetConverter implements AttributeConverter<Set<AlarmFrequencyType>, String> {

    private static final String SEPARATOR = "/";


    @Override
    public String convertToDatabaseColumn(Set<AlarmFrequencyType> attribute) {
        StringBuilder s = new StringBuilder();
        attribute.forEach((alarmTurnOffType) -> s.append(alarmTurnOffType.toString()));
        return s.toString();
    }

    @Override
    public Set<AlarmFrequencyType> convertToEntityAttribute(String dbData) {
        String[] strings = dbData.split(SEPARATOR);
        Set<AlarmFrequencyType> alarmTurnOffTypes = new HashSet<>();

        for (String s :
                strings) {
            alarmTurnOffTypes.add(AlarmFrequencyType.valueOf(s));
        }
        return alarmTurnOffTypes;
    }
}
