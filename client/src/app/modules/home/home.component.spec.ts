import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeComponent } from './home.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { TransactionsTableComponent } from './components/transactions-table/transactions-table.component';
import { BalanceComponent } from './components/balance/balance.component';
import { CardComponent } from './components/card/card.component';
import { LastTransactionsComponent } from './components/last-transactions/last-transactions.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { TopBarComponent } from './components/top-bar/top-bar.component';
import { MonthlyBalanceCardComponent } from './components/monthly-balance-card/monthly-balance-card.component';
import { CardsComponent } from './pages/cards/cards.component';
import { OperationsComponent } from './pages/operations/operations.component';
import { CommonModule } from '@angular/common';
import { HomeRoutingModule } from './home-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        HomeComponent,
        SidebarComponent,
        TransactionsTableComponent,
        BalanceComponent,
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
        HomeRoutingModule,
        HttpClientModule,
        HttpClientTestingModule
      ],
    });
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
