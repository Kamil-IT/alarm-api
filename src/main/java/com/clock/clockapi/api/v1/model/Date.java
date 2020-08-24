package com.clock.clockapi.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Date {

    public static final String SEPARATOR = "-";

    private Integer day;
    private Integer month;
    private Integer year;

    public Date(String fromString){
        this.day = Integer.parseInt(fromString.substring(0, 2));
        this.month = Integer.parseInt(fromString.substring(3, 5));
        this.year = Integer.parseInt(fromString.substring(6));
    }

    /**
     * @return date with simply format: DD-MM-YYYY
     */
    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder();
        if (day < 10){
            returnString.append("0").append(day);
        }
        else {
            returnString.append(day);
        }
        returnString.append(SEPARATOR);
        if (month < 10){
            returnString.append("0").append(month);
        }
        else {
            returnString.append(month);
        }
        returnString.append(SEPARATOR);
        if (year < 10){
            returnString.append("0").append(year);
        }
        else {
            returnString.append(year);
        }
        return returnString.toString();
    }
}
