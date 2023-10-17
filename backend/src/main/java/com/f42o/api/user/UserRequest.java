package com.f42o.api.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private CredentialType credentialType;
    private String credentialNumber;
    private String password;
    private String fullName;
}
