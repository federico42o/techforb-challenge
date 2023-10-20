import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MonthlyBalanceCardComponent } from './monthly-balance-card.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpClientModule } from '@angular/common/http';

describe('MonthlyBalanceCardComponent', () => {
  let component: MonthlyBalanceCardComponent;
  let fixture: ComponentFixture<MonthlyBalanceCardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MonthlyBalanceCardComponent],
      imports: [HttpClientModule, HttpClientTestingModule],
    });
    fixture = TestBed.createComponent(MonthlyBalanceCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
