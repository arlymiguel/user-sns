package com.qcaldwell.user.controller;


import com.qcaldwell.user.dto.UserDto;
import com.qcaldwell.user.dto.UserMessageDto;
import com.qcaldwell.user.service.UserService;
import com.qcaldwell.user.sns.UserPublisher;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
    private final UserPublisher userPublisher;
    private final UserService userService;

    @GetMapping(value = "/{id}")
    public UserDto getUser(@PathVariable("id")Long id) {
        log.info("Calling get user API");
        UserMessageDto message = UserMessageDto.builder().message("New Message Sent to Vehicle")
                .build();
        userPublisher.sendMessage(message);
        return userService.getUser(id);
    }
    @PostMapping
    public UserDto saveUser(@RequestBody @Valid UserDto userDto) {
        log.info("Saving UserDto");
        return userService.save(userDto);
    }
    @PutMapping(value = "/{id}")
    public UserDto updateUser(@RequestBody UserDto userDto,
                             @PathVariable("id")Long id) {
        log.info("Updating UserDto");
        return userService.update(userDto,id);
    }
    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable("id")Long id) {
        userService.delete(id);
    }

}
