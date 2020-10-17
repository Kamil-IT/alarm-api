package com.clock.clockapi.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Date {

    public static final String SEPARATOR = "-";

    @Min(1)
    @Max(31)
    @ApiModelProperty(required = true)
    private Integer day;

    @Min(1)
    @Max(12)
    @ApiModelProperty(required = true)
    private Integer month;

    @Min(1970)
    @Max(2100)
    @ApiModelProperty(required = true)
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
