package com.f42o.api.auth;

import com.f42o.api.user.CredentialType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotNull
    private CredentialType credentialType;
    @NotBlank
    private String credentialNumber;
    @NotNull
    @Size(min = 6)
    private String password;
    @NotBlank
    @Size(min = 2,max = 60)
    private String fullName;
}