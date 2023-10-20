import { Injectable } from "@angular/core";
import { AbstractControl, AsyncValidatorFn, ValidationErrors } from "@angular/forms";
import { Observable, debounceTime, map } from "rxjs";
import { AuthService } from "../services";

@Injectable({ providedIn: 'root' })
export class UniqueUserValidator{
  static createValidator(authService: AuthService): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      return authService.userAlreadyExist(control.value)
        .pipe(
          map((result) =>
            result ? { userAlreadyExists: true } : null
          )
        );
    };
  }
}