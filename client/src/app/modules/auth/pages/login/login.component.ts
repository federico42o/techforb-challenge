import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services';
import { LoginRequest } from 'src/app/models/login-request';
import { Router } from '@angular/router';
import { animate, state, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  animations: [
    trigger('ngAnimate', [
      state('true', style({ opacity: 1 })),
      state('false', style({ opacity: 0 })),
      transition('true => false', animate('0.5s'))
    ])
  ]
})
export class LoginComponent implements OnInit {
  
  isTypeFocused: boolean = false;
  isDniFocused: boolean = false;
  isPasswordFocused: boolean = false;
  showPassword:boolean = false;
  showIcon:boolean = false;
  error:boolean=false;
  form!:FormGroup;
  errorMessage:string = '';
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  ngOnInit(): void {
    
    this.form = this.fb.group({
      credentialType:[null,[Validators.required]],
      credentialNumber:[null,[Validators.required,Validators.minLength(7), Validators.maxLength(8)]],
      password:[null,[Validators.required,Validators.maxLength(64)]],
    });
  }

  get credentialType(){
    return this.form.get('credentialType');
  }
  get credentialNumber(){
    return this.form.get('credentialNumber');
  }
  get password(){
    return this.form.get('password');
  }

  touched(field:string):boolean{
    return this.form.get(field)?.invalid && this.form.get(field)?.touched || false;
  }

  onError(err: Error) {
    this.errorMessage = err.message;
    this.error = true;
  
  setTimeout(() => {
      this.clearError();
    }, 3000);
  }
  
  private clearError() {
    this.error = false;
  }
  onSubmit():void{
    if(this.form.invalid){
      return
    }
    const request:LoginRequest = this.form.value;

    this.authService.login(request).subscribe({
      next:()=>{
        this.router.navigate(["/inicio"])
      },
      error:(err:Error)=>{
        this.onError(err)
      }
    });
  }


}
