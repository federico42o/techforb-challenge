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
import { MonthlyBalanceCardComponent } from './components/monthly-balance-card/monthly-balance-card.component';
import { CardsComponent } from './pages/cards/cards.component';
import { OperationsComponent } from './pages/operations/operations.component';


@NgModule({
  declarations: [
    SidebarComponent,
    TransactionsTableComponent,
    BalanceComponent,
    HomeComponent,
    CardComponent,
    LastTransactionsComponent,
    DashboardComponent,
    TopBarComponent,
    MonthlyBalanceCardComponent,
    CardsComponent,
    OperationsComponent
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
