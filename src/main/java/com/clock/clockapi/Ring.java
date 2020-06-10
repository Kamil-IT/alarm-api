package com.clock.clockapi;

import com.clock.clockapi.api.v1.model.alarm.ring.RingType;

import java.io.File;

public interface Ring {

//    TODO: have to sent MP3 ring
    File getRing();

    RingType getRingType();
}
