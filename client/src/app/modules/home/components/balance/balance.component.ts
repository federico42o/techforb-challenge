import { Component, Inject, Input, OnInit, inject } from '@angular/core';
import { Chart,ChartData,registerables  } from 'chart.js';
import { AccountService } from '../../services/account.service';
import { BalanceService } from '../../services/balance.service';
import { User } from 'src/app/models/user';
import { Balance } from 'src/app/models/balance';
import { BalanceType } from 'src/app/models/balance-type';
import { AuthService } from 'src/app/modules/auth/services';
Chart.register(...registerables);
@Component({
  selector: 'app-balance',
  templateUrl: './balance.component.html',
  styleUrls: ['./balance.component.css']
})
export class BalanceComponent implements OnInit{
  private authService = inject(AuthService)
  private balanceService = inject(BalanceService);
  user!:User;
  dataLoaded: boolean = false;
  labelData!: string[];
  valueData!: number[];
  chart!: any;
  balanceType:BalanceType = BalanceType.WEEKLY
  
  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe({
      next:(user:User|null)=>{
        if(user){
          this.user = user;
          this.loadData(user.id,this.setDate(BalanceType.WEEKLY),new Date());
          
        }

      },
      complete:()=>{
        
      }
    })
    
    }
  
  createChart(id:string,labels:any,data:any): void {
    this.chart = new Chart(id, {
      type: 'bar',
      
      data: {
        labels: labels,
        datasets: [{
          label: 'balance',
          data: data,
          borderWidth: 1,
          maxBarThickness: 30,
          backgroundColor:"#76C1A1",
        
          
          
        },
       
      ]
      },
      options: {
        color:"#fff",
        maintainAspectRatio:false,
        plugins:{
          tooltip:{
            padding:26,
            callbacks:{
              beforeTitle:function(context){
                return "BALANCE"
              },
              title:function(context){
                return context[0].label
              },
              afterTitle:function(context){

                return Number(context[0].raw).toLocaleString("en-US",{style:"currency", currency:"USD"});
              },
              label:function(context){return}
            }
           
          },
          

        },
        scales: {
          y: {
            
            beginAtZero: true,
            grid:{
              color:"#767676",
            },
            ticks:{
              callback: function(value, index, values) {
                return value.toLocaleString("en-US",{style:"currency", currency:"USD"});
              },
              color:"#fff",
            }
          },
          x:{
            grid:{
              display:false,
              color:"#767676",
            },
            ticks:{
              color:"#fff",
            }
          }

        },
        locale: 'es',
      },

    });

  }
  applyFilter(event:Event):void{
    this.chart.destroy();
    const filterValue = (event.target as HTMLInputElement).value;
    const end = new Date();
    this.balanceType = filterValue == BalanceType.WEEKLY ? BalanceType.WEEKLY : BalanceType.MONTHLY 
    this.loadData(this.user.id,this.setDate(filterValue),end);
  }




  loadData(clientId:string,start:Date,end:Date): void {

      this.balanceService.getBalance(clientId,start,end,this.balanceType).subscribe({
        next:(response:Balance)=>{
          this.labelData = this.formatLabels(response.labels)
          this.valueData = response.values
        },
        complete:()=>{
          this.createChart('bar-chart',this.labelData,this.valueData);
        }
      })

  }

  formatLabels(labels:string[]){
    return labels.map(l=>l.slice(0,3));
  }
  formatValues(values: number[]): string[] {
    return values.map(value => `$${value}`);
  }


  setDate(type:string) : Date{
    const start = new Date();
    if (type === "WEEKLY") {
      const today = start.getUTCDay(); 
      const daysToMonday = today === 0 ? 6 : today - 1; 
      start.setUTCDate(start.getUTCDate() - daysToMonday); 
      start.setUTCHours(0, 0, 0, 0); 
    } else if (type === "MONTHLY") {
      start.setUTCMonth(0, 1); 
      start.setUTCHours(0, 0, 0, 0); 
    }
    console.log(start)
    return start;
  }
}
