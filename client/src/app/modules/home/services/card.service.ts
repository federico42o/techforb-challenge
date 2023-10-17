import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Card } from 'src/app/models/card';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CardService {

  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl

  getByClientId(clientId:string):Observable<Card[]>{
    return this.http.get<Card[]>(`${this.apiUrl}/cards/${clientId}`);
  }
}
