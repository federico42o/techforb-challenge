package com.f42o.api.auth;

import com.f42o.api.user.CredentialType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotNull
    private CredentialType credentialType;
    @NotBlank
    @Size(min = 7,max=8)
    private String credentialNumber;
    @NotBlank
    @Size(min = 6)
    private String password;
}
