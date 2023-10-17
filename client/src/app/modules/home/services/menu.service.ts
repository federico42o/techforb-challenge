import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { MenuItem } from 'src/app/models/menu-item';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  private http = inject(HttpClient)
  private menuItems = new BehaviorSubject<MenuItem[]>([]);
  private apiUrl = environment.apiUrl

  getMenuItems(){
    return this.menuItems.asObservable();
  }

  setMenuItems(menuItems:MenuItem[]){
    this.menuItems.next(menuItems);
  }

  addMenuItem(newMenuItem: MenuItem) {
    const currentItems = this.menuItems.value;
    currentItems.push(newMenuItem); 
    this.menuItems.next(currentItems);
  }


  getAll():Observable<MenuItem[]>{
    return this.http.get<MenuItem[]>(`${this.apiUrl}/menu`);
  }

}
