package com.clock.clockapi.api.v1.conventer;

import com.clock.clockapi.api.v1.model.Date;
import com.clock.clockapi.api.v1.model.alarm.frequency.AlarmFrequencyType;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.util.*;

@Component
@Convert
public class AlarmFrequencyCostumeListConverter implements AttributeConverter<List<Date>, String> {

    private static final String SEPARATOR = " ";

    @Override
    public String convertToDatabaseColumn(List<Date> attribute) {
        if (attribute == null){
            return "";
        }
        else if (attribute.isEmpty()){
            return "";
        }
        StringBuilder s = new StringBuilder();
        attribute.forEach((alarmFrequency) -> s.append(alarmFrequency.toString()).append(SEPARATOR));
        String sAddSepa = s.toString();
        return sAddSepa.substring(0, sAddSepa.length()-1);
    }

    @Override
    public List<Date> convertToEntityAttribute(String dbData) {
        if (dbData == null){
            return Collections.emptyList();
        }
        String[] strings = dbData.split(SEPARATOR);
        List<Date> dates = new ArrayList<>();

        for (String s :
                strings) {
            if (!s.isBlank()){
                dates.add(new Date(s));
            }
        }
        return dates;
    }
}
