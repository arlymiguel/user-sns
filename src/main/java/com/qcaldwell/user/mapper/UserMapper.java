package com.qcaldwell.user.mapper;

import com.qcaldwell.user.dto.UserDto;
import com.qcaldwell.user.model.User;
import org.springframework.stereotype.Component;

@Component
@Deprecated
public class UserMapper {
    public User toUser(UserDto userDto){
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .build();
    }

    public UserDto toUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
