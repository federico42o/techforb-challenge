import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { Subscription } from 'rxjs';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/modules/auth/services';
import { AccountService } from '../../services/account.service';
import { BankAccountDto } from 'src/app/models/bank-account-dto';
import { BankAccount } from 'src/app/models/bank-account';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit,OnDestroy{

  
  private authService = inject(AuthService)
  private accountService = inject(AccountService)
  private authServiceSubscription!: Subscription;
  private accountServiceSubscription!: Subscription;
  bankAccount!:BankAccount;
  user!:User;
  ngOnInit(): void {
      this.authServiceSubscription = this.authService.getCurrentUser().subscribe({
        next:(response:User|null)=>{
          if(response){
            this.user = response;
            this.getBankAccount(response.id);
          }
        }
      })


  }

  getBankAccount(clientId:string) {
    this.accountServiceSubscription = this.accountService.getByClientId(clientId).subscribe({
      next: (response: BankAccount) => {
        if (response) {
          this.bankAccount = response;
        }
      },
    });
  }

  ngOnDestroy(): void {
    if (this.authServiceSubscription) {
      this.authServiceSubscription.unsubscribe();
    }
    if (this.accountServiceSubscription) {
      this.accountServiceSubscription.unsubscribe();
    }
  }
}
