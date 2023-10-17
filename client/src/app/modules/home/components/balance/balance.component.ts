import { Component, Inject, OnInit, inject } from '@angular/core';
import { Chart,ChartData,registerables  } from 'chart.js';
import { AccountService } from '../../services/account.service';
Chart.register(...registerables);
@Component({
  selector: 'app-balance',
  templateUrl: './balance.component.html',
  styleUrls: ['./balance.component.css']
})
export class BalanceComponent implements OnInit{
  private accountService = inject(AccountService);
  dataLoaded: boolean = false;
  labelData!: any;
  mainData!: any;
  chart!: any;
  colors!: string[];
  today:Date = new Date();
  ctx:string = 'pie-chart'
  todayString:string = this.today.toISOString().substring(0, 10);
  yesterday:Date = new Date(this.today.getFullYear(),this.today.getMonth(),this.today.getDate()-1);
  ngOnInit(): void {
      const labels = ["LUNES","MARTES","MIERCOLES","JUEVES","VIERNES","SABADO","DOMINGO"]
      this.createChart('bar-chart',labels);
    }
  
  createChart(id:string,labels:any): void {
    const footer = (tooltipItems: any[]) => {
      // Obtiene los valores de ambas barras
      let balance = "";
      // Calcula el balance (diferencia)
      tooltipItems.forEach(
        function(tooltipItem){
          balance = ((tooltipItem.parsed.y - tooltipItem.parsed.x) * 100).toFixed(2)
        }
      )

    
    
      return `%: ${balance}`;
    };
    this.chart = new Chart(id, {
      type: 'bar',
      
      data: {
        labels: labels,
        datasets: [{
          label: '# Semana anterior',
          data: [15, 52, 52, 21, 25, 25, 23],
          borderWidth: 1,
          maxBarThickness: 50,
          backgroundColor:"rgba(75, 192, 192)",
          
          
        },
        {
          label: '# Esta semana',
          data: [25, 52, 51, 21, 256, 155, 42],
          borderWidth: 1,
          maxBarThickness: 50,
          backgroundColor:"rgba(54, 162, 235)"
          
        },
        
      ]
      },
      options: {
        plugins:{
          tooltip:{
            padding:26,
            callbacks: {
              footer: footer,
            }  
            
          },
          
          title:{
            text:"Balance"
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            grid:{
              lineWidth:3,
            }
          },
          x:{
            grid:{
              display:false
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
    const date = new Date(filterValue)
    this.loadData(date);
  }

  _updateData(date:string){
    const newDate = new Date(date)
    this.loadData(newDate);
  }
  setLabels(data: any[]): void {
    this.labelData = data.map((d: any) => d.game);
  }

  setMainData(data: any[]): void {
    this.mainData = data.map((d: any) => d.totalTicketsSold);
  }
  loadData(date:Date): void {

  }
}
