import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MonthlyBalanceCardComponent } from './monthly-balance-card.component';

describe('MonthlyBalanceCardComponent', () => {
  let component: MonthlyBalanceCardComponent;
  let fixture: ComponentFixture<MonthlyBalanceCardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MonthlyBalanceCardComponent]
    });
    fixture = TestBed.createComponent(MonthlyBalanceCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
