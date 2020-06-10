package com.clock.clockapi.security.mapper;

import com.clock.clockapi.security.model.UserApp;
import com.clock.clockapi.security.model.UserAppDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAppMapper {


//    TODO: implement it and test


    UserApp UserAppDtoToUserApp(UserAppDto userAppDto);

    UserAppDto UserAppToUserAppDto(UserApp userApp);
}
