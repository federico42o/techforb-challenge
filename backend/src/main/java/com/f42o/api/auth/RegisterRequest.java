package com.f42o.api.auth;

import com.f42o.api.user.CredentialType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private CredentialType credentialType;
    private String credentialNumber;
    private String password;
    private String fullName;
}