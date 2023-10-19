import { Component, Input, OnChanges, OnInit, inject } from '@angular/core';
import { TransactionsService } from '../../services/transactions.service';
import { AuthService } from 'src/app/modules/auth/services';
import { User } from 'src/app/models/user';
import { MonthlyTransactionsDto } from 'src/app/models/monthly-transactions-dto';

@Component({
  selector: 'app-monthly-balance-card',
  templateUrl: './monthly-balance-card.component.html',
  styleUrls: ['./monthly-balance-card.component.css']
})
export class MonthlyBalanceCardComponent implements OnInit{

  
  @Input() title:string = "Ingresos"

  private transactionService = inject(TransactionsService)
  private authService = inject(AuthService)
  private clientId:string="";
  monthlySummary!:MonthlyTransactionsDto;
  ngOnInit(): void {
      this.authService.getCurrentUser().subscribe({
        next:(response:User|null)=>{
          if(response){
            this.clientId = response.id
            this.loadData(this.clientId,this.title);
          }
        }
      })
  }
  setIcon(title:string):string{
    if(title.toLowerCase() === "incomes"){
      return "/assets/icons/arrow-up.png" 
    }
    if(title.toLowerCase() === "expenses"){
      return "/assets/icons/arrow-down.png" 
    }

    return ""
  }
  setArrow(value:number):string{
    if(value>0){
      return "trending_up"
    }
    if(value<0){
      return "trending_down"
    }
    return "unfold_less"
  }

  loadData(clientId:string, summaryType:string){
    this.transactionService.getMonthlySummary(clientId,summaryType.toLowerCase()).subscribe({
      next:(response:MonthlyTransactionsDto)=>{
        this.monthlySummary = response
      }
    })
  }
}
