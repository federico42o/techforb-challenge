import { CredentialType } from "./credential-type";

export interface User {
    fullName:string,
    credentialType:CredentialType,
    credentialNumber:string;
}
