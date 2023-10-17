import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { TransactionsTableComponent } from './components/transactions-table/transactions-table.component';
import { BalanceComponent } from './components/balance/balance.component';
import { HomeComponent } from './home.component';
import { CardComponent } from './components/card/card.component';
import { LastTransactionsComponent } from './components/last-transactions/last-transactions.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { TopBarComponent } from './components/top-bar/top-bar.component';


@NgModule({
  declarations: [
    SidebarComponent,
    TransactionsTableComponent,
    BalanceComponent,
    HomeComponent,
    CardComponent,
    LastTransactionsComponent,
    DashboardComponent,
    TopBarComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule
  ],
  exports:[
    SidebarComponent,
    TransactionsTableComponent,
    BalanceComponent,
    HomeComponent
  ]
})
export class HomeModule { }
