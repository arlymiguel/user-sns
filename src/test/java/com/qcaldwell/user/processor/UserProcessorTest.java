package com.qcaldwell.user.processor;
import com.qcaldwell.user.dto.UserDto;
import com.qcaldwell.user.exception.UserNotFoundException;
import com.qcaldwell.user.mapper.UserNewMapper;
import com.qcaldwell.user.model.User;
import com.qcaldwell.user.repository.UserRepository;
import com.qcaldwell.user.processor.UserProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserProcessorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserProcessor userProcessor;

    @Test
    void testSave() {
        // Arrange
        UserDto userDto = UserDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .build();

        User userToSave = UserNewMapper.INSTANCE.fromDto(userDto);
        userToSave.setCreatedBy(userDto.email());
        userToSave.setUpdatedBy(userDto.email());

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setFirstName("John");
        savedUser.setLastName("Doe");
        savedUser.setEmail("john@example.com");

        //Mockito.when(userRepository.save(userToSave)).thenReturn(savedUser);
        when(userRepository.save(any())).thenReturn(savedUser);

        // Act
        UserDto savedUserDto = userProcessor.save(userDto);

        // Assert
        Assertions.assertEquals(1L, savedUserDto.id());
        Assertions.assertEquals("John", savedUserDto.firstName());
        Assertions.assertEquals("Doe", savedUserDto.lastName());
        Assertions.assertEquals("john@example.com", savedUserDto.email());

        Mockito.verify(userRepository, Mockito.times(1)).save(any());
    }

    @Test
    void testUpdate_UserFound() {
        // Arrange
        Long userId = 1L;
        UserDto userDto = UserDto.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setFirstName("Alice");
        existingUser.setLastName("Smith");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setFirstName("John");
        updatedUser.setLastName("Doe");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        // Act
        UserDto updatedUserDto = userProcessor.update(userDto, userId);

        // Assert
        Assertions.assertEquals(userId, updatedUserDto.id());
        Assertions.assertEquals("John", updatedUserDto.firstName());
        Assertions.assertEquals("Doe", updatedUserDto.lastName());

        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
        Mockito.verify(userRepository, Mockito.times(1)).save(existingUser);
    }

    @Test
    void testUpdate_UserNotFound() {
        // Arrange
        Long userId = 1L;
        UserDto userDto = UserDto.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThrows(UserNotFoundException.class, () -> userProcessor.update(userDto, userId));

        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
        Mockito.verify(userRepository, Mockito.never()).save(any(User.class));
    }

    @Test
    void testDelete() {
        // Arrange
        Long userId = 1L;

        // Act
        userProcessor.delete(userId);

        // Assert
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userId);
    }

}
