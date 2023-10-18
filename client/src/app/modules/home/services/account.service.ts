import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { BankAccount } from 'src/app/models/bank-account';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private http= inject(HttpClient);
  private apiUrl = environment.apiUrl;

  getByClientId(clientId:string):Observable<BankAccount>{
    return this.http.get<BankAccount>(`${this.apiUrl}/accounts/${clientId}`);
  }
}
