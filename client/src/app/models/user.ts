import { CredentialType } from "./credential-type";

export interface User {
    id:string,
    fullName:string,
    credentialType:CredentialType,
    credentialNumber:string;
}
