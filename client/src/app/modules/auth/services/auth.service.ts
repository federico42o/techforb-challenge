import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { BehaviorSubject, Observable, catchError, switchMap, tap, throwError } from 'rxjs';
import { LoginRequest } from 'src/app/models/login-request';
import { RegisterRequest } from 'src/app/models/register-request';
import { User } from 'src/app/models/user';
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
  constructor(){this.initializeCurrentUser()}

  private initializeCurrentUser(): void {
    
    if (this.cookie.check('token')) {
      const token = this.cookie.get('token');
      const decoded: any = this.decodeToken(token);

      this.getUserByCredential(decoded.sub).subscribe({
        next: (user: User) => {
          this.setCurrentUser(user);

        },
        error: () => {

          this.cookie.deleteAll();
        }
      });
    }
  }



  login(request:LoginRequest):Observable<any>{
    return this.http.post(`${this.authUrl}/login`,request).pipe(
      switchMap((res: any) => {
        this.cookie.set('token', res.token,{expires:1,path:'/'});
        const decoded: any = this.decodeToken(res.token);
        return this.getUserByCredential(decoded.sub);
      }),
      tap((user: User) => {
        this.setCurrentUser(user);
        
      }),
      catchError((err:any) => {
        this.cookie.delete('token', '/');
        this.setCurrentUser(null);
        return throwError(()=> new Error('Usuario o contraseña incorrectos.'));
      })
      
    );
  }

  register(request:RegisterRequest):Observable<any>{
    return this.http.post(`${this.authUrl}/register`,request);
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


  getCurrentUser(){
    return this.currentUserSubject.asObservable();
  }


  isLoggedIn() {
    const token = this.cookie.get('token');
    if(token !== '' && token !== null){
      const tokenExpiration = new Date(this.decodeToken(token).exp*1000)
      const currentDate = new Date();

      return currentDate<tokenExpiration;
    }
    return false;
  }

  userAlreadyExist(credentialNumber:string){
    return this.http.get(`${this.apiUrl}/users/check/${credentialNumber}`);
  }

  decodeToken(token:any):any{
    const decodedPayload = {sub:"",exp:""}
    const payload = token.split('.')[1];
    const base64 = payload.replaceAll('-', '+').replaceAll('_', '/');
    decodedPayload.sub = JSON.parse(atob(base64)).sub;
    decodedPayload.exp = JSON.parse(atob(base64)).exp;
    return decodedPayload;
  }
  
}
