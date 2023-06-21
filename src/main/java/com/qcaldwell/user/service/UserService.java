package com.qcaldwell.user.service;

import com.qcaldwell.user.dto.UserDto;
import com.qcaldwell.user.processor.UserProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserProcessor userProcessor;
    public UserDto getUser(Long id) {
        return userProcessor.getUser(id);
    }

    public UserDto save(UserDto userDto) {
        return userProcessor.save(userDto);
    }

    public UserDto update(UserDto userDto, Long id) {
        return userProcessor.update(userDto,id);
    }

    public void delete(Long id) {
        userProcessor.delete(id);
    }
}
