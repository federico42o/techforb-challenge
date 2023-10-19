import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, PatternValidator, Validators } from '@angular/forms';
import { AuthService } from '../../services';
import { Router } from '@angular/router';
import { RegisterRequest } from 'src/app/models/register-request';
import { UniqueUserValidator } from '../../utils/unique-user-validator';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  
  isTypeFocused: boolean = false;
  isDniFocused: boolean = false;
  isPasswordFocused: boolean = false;
  isNameFocused: boolean = false;
  showPassword:boolean = false;
  showIcon:boolean = false;
  form!:FormGroup;
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  private uniqueUserValidator = inject(UniqueUserValidator)
  ngOnInit(): void {
    this.form = this.fb.group({
      credentialType:[null,[Validators.required]],
      fullName:[null,[Validators.required,Validators.minLength(2), Validators.maxLength(30)]],
      password:[null,[Validators.required,Validators.maxLength(64)]],
      credentialNumber: [
        null,
        {
          validators: [Validators.required,Validators.minLength(7), Validators.maxLength(8)],
          asyncValidators: [UniqueUserValidator.createValidator(this.authService)],
          updateOn: 'blur'
        }
      ]
    });
  }

  get credentialType(){
    return this.form.get('credentialType');
  }
  get credentialNumber(){
    return this.form.get('credentialNumber');
  }
  get fullName(){
    return this.form.get('fullName');
  }
  get password(){
    return this.form.get('password');
  }

  touched(field:string):boolean{
    return this.form.get(field)?.invalid && this.form.get(field)?.touched || false;
  }
  onSubmit():void{
    const request:RegisterRequest = this.form.value;
    if(this.form.invalid){
      return
    }
    this.authService.register(request).subscribe({
      next:()=>{
        this.router.navigate(["/inicio"])
      }
    });
  }

}
