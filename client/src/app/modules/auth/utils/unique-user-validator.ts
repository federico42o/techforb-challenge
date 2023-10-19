import { Injectable } from "@angular/core";
import { AbstractControl, AsyncValidatorFn, ValidationErrors } from "@angular/forms";
import { Observable, debounceTime, map } from "rxjs";
import { AuthService } from "../services";

@Injectable({ providedIn: 'root' })
export class UniqueUserValidator{
  static createValidator(authService: AuthService): AsyncValidatorFn {
    console.log('Starting async validation...')
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      console.log('Starting async validation...')
      return authService.userAlreadyExist(control.value)
        .pipe(
          map((result) =>
            result ? { userAlreadyExists: true } : null
          )
        );
    };
  }
}