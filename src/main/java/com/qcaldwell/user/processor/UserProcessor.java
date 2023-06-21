package com.qcaldwell.user.processor;

import com.qcaldwell.user.dto.UserDto;
import com.qcaldwell.user.exception.UserNotFoundException;
import com.qcaldwell.user.mapper.UserMapper;
import com.qcaldwell.user.mapper.UserNewMapper;
import com.qcaldwell.user.model.User;
import com.qcaldwell.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserProcessor {
    private final UserRepository userRepository;
    private static final UserNewMapper mapper = UserNewMapper.INSTANCE;
    //private final UserMapper userMapper;

    public UserDto getUser(Long id) {
        Optional<User> userFound = userRepository.findById(id);
        if (userFound.isPresent()) {
            //return userMapper.toUserDto(userFound.get());
            return mapper.toDto(userFound.get());
        }
        else {
            throw new UserNotFoundException("The user does not exist");
        }
    }

    public UserDto save(UserDto userDto) {
        //User userToSave = userMapper.toUser(userDto);
        User userToSave = mapper.fromDto(userDto);
        userToSave.setCreatedBy(userDto.getEmail());
        userToSave.setUpdatedBy(userDto.getEmail());
        User userSaved = userRepository.save(userToSave);
        //return userMapper.toUserDto(userSaved);
        return mapper.toDto(userSaved);
    }

    public UserDto update(UserDto userDto, Long id) {
        Optional<User> userFound = userRepository.findById(id);
        if (userFound.isPresent()) {
            User userToUpdate = userFound.get();
            userToUpdate.setFirstName(userDto.getFirstName());
            userToUpdate.setLastName(userDto.getLastName());
            userToUpdate.setEmail(userDto.getEmail());
            User userUpdated = userRepository.save(userToUpdate);
            //return userMapper.toUserDto(userUpdated);
            return mapper.toDto(userUpdated);
        } else {
            throw new UserNotFoundException("The user does not exist");
        }
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
