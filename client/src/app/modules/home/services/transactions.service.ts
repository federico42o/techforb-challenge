import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from 'src/app/models/page';
import { Transaction } from 'src/app/models/transaction';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TransactionsService {

  private http = inject(HttpClient)
  private apiUrl = environment.apiUrl

  getAllByClientId(clientId:string,page:number =0,size:number=5):Observable<Page<Transaction>>{
    return this.http.get<Page<Transaction>>(`${this.apiUrl}/transactions/${clientId}?page=${page}&size=${size}`);

  }
}
