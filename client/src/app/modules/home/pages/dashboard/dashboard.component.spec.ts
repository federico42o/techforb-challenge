import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardComponent } from './dashboard.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpClientModule } from '@angular/common/http';
import { CardComponent } from '../../components/card/card.component';
import { MonthlyBalanceCardComponent } from '../../components/monthly-balance-card/monthly-balance-card.component';
import { BalanceComponent } from '../../components/balance/balance.component';
import { LastTransactionsComponent } from '../../components/last-transactions/last-transactions.component';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DashboardComponent,CardComponent,MonthlyBalanceCardComponent,BalanceComponent,LastTransactionsComponent],
      imports: [HttpClientModule, HttpClientTestingModule],
    });
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
