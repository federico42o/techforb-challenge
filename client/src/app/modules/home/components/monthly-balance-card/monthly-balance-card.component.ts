import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-monthly-balance-card',
  templateUrl: './monthly-balance-card.component.html',
  styleUrls: ['./monthly-balance-card.component.css']
})
export class MonthlyBalanceCardComponent {

  @Input() title:string = "Ingresos"



  setIcon(title:string):string{
    if(title.toLowerCase() === "ingresos"){
      return "/assets/icons/arrow-up.png" 
    }
    if(title.toLowerCase() === "egresos"){
      return "/assets/icons/arrow-down.png" 
    }

    return ""
  }
}
