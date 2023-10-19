import { CredentialType } from "./credential-type";

export interface RegisterRequest {
    credentialType:CredentialType,
    credentialNumber:string,
    fullName:string,
    password:string

}

