package com.qcaldwell.user.mapper;

import com.qcaldwell.user.dto.UserDto;
import com.qcaldwell.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserNewMapper {
    UserNewMapper INSTANCE = Mappers.getMapper(UserNewMapper.class);
    //@Mapping(target = "firstName", source = "firstName")
    //@Mapping(target = "lastName", source = "lastName")
    //@Mapping(target = "email", source = "email")
    UserDto toDto(User user);

    //@Mapping(target = "firstName", source = "firstName")
    //@Mapping(target = "lastName", source = "lastName")
    //@Mapping(target = "email", source = "email")
    User fromDto(UserDto userDto);
}
