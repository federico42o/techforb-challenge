import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { BehaviorSubject, Observable, catchError, switchMap, tap, throwError } from 'rxjs';
import { User } from 'src/app/models/user';
import { UserRequest } from 'src/app/models/user-request';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  apiUrl = environment.apiUrl
  authUrl = environment.authUrl
  private cookie = inject(CookieService);
  private http = inject(HttpClient);
  private currentUserSubject = new BehaviorSubject<User|null>(null);
  private userLogged = new BehaviorSubject<Boolean>(false);
  constructor(){this.initializeCurrentUser()}

  private initializeCurrentUser(): void {
    
    if (this.cookie.check('token')) {
      const token = this.cookie.get('token');
      const decoded: any = this.decodeToken(token);

      this.getUserByCredential(decoded).subscribe({
        next: (user: User) => {
          this.setCurrentUser(user);

        },
        error: (err: any) => {

          this.cookie.deleteAll();
        }
      });
    }
  }



  login(request:UserRequest):Observable<any>{
    return this.http.post(`${this.authUrl}/login`,request).pipe(
      switchMap((res: any) => {
        this.cookie.set('token', res.token,{expires:1,path:'/'});
        const decoded: any = this.decodeToken(res.token);
        return this.getUserByCredential(decoded);
      }),
      tap((user: User) => {
        this.setCurrentUser(user);
        this.setUserLogged(true)
        
      }),
      catchError(() => {
        this.cookie.delete('token', '/');
        this.setCurrentUser(null);
        return throwError(()=> new Error('Usuario o contraseña incorrectos.'));
      })
      
    );
  }

  logout(){
    this.cookie.deleteAll();
    location.reload();
  }

  getUserByCredential(credential: string): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/users/${credential}`);
  }

  setCurrentUser(user:User | null){
    this.currentUserSubject.next(user);
  }
  setUserLogged(state:boolean){
    this.userLogged.next(state);
  }

  getCurrentUser(){
    return this.currentUserSubject.asObservable();
  }
  getUserLogged(){
    return this.userLogged.asObservable();
  }

  isLoggedIn(): boolean {
    const token = this.cookie.get('token');
    return token !== '' && token !== null;
  }

  private decodeToken(token:string):string{
    const payload = token.split('.')[1];
    const base64 = payload.replaceAll('-', '+').replaceAll('_', '/');
    const decodedPayload = JSON.parse(atob(base64));
    return decodedPayload.sub;
  }
}
