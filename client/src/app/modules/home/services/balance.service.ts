import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Balance } from 'src/app/models/balance';
import { BalanceType } from 'src/app/models/balance-type';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BalanceService {

  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl

  getBalance(clientId:string,startDate:Date,endDate:Date,balanceType:BalanceType):Observable<Balance>{
    const queryParams = new HttpParams()
    .set('clientId', clientId)
    .set('start', startDate.toISOString().slice(0, -5))
    .set('end', endDate.toISOString().slice(0, -5))
    .set('balanceType',balanceType.toString())
    return this.http.get<Balance>(`${this.apiUrl}/balance`,{params:queryParams});
  }
}
