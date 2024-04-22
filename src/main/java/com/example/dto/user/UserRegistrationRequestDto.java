package com.example.dto.user;

import com.example.validation.Email;
import com.example.validation.FieldMatch;
import com.example.validation.Password;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "repeatPassword",
        message = "the 'password' and 'repeatPassword' fields don't match")
})
@Data
public class UserRegistrationRequestDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Password
    private String password;
    @NotBlank
    private String repeatPassword;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String shippingAddress;
}
