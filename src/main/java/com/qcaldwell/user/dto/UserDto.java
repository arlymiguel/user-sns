package com.qcaldwell.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto{
        private Long id;
        private String firstName;
        private String lastName;
        private @NotBlank String email;

}


