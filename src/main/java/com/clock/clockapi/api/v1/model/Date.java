package com.clock.clockapi.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Date {

    private Integer day;
    private Integer month;
    private Integer year;
}
