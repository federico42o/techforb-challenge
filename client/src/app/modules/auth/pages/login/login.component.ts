import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services';
import { UserRequest } from 'src/app/models/user-request';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  isTypeFocused: boolean = false;
  isDniFocused: boolean = false;
  isPasswordFocused: boolean = false;
  form!:FormGroup;
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private cookie = inject(CookieService);
  private router = inject(Router);
  ngOnInit(): void {
    
    this.form = this.fb.group({
      credentialType:["",[Validators.required]],
      credentialNumber:["",[Validators.required]],
      password:["",[Validators.required]]
    });
  }

  onSubmit():void{
    if(this.form.invalid){
      return
    }
    const request:UserRequest = {
      credentialType : this.form.get('credentialType')?.value,
      credentialNumber : this.form.get('credentialNumber')?.value,
      password : this.form.get('password')?.value,

    } 
    this.authService.login(request).subscribe({
      next:()=>{
        this.router.navigate(["/"])
      }
    });
  }


}
