import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LastTransactionsComponent } from './last-transactions.component';
import { AuthService } from 'src/app/modules/auth/services';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpClientModule } from '@angular/common/http';
import { SimpleChange, SimpleChanges } from '@angular/core';
import { User } from 'src/app/models/user';
import { CredentialType } from 'src/app/models/credential-type';

describe('LastTransactionsComponent', () => {
  let component: LastTransactionsComponent;
  let fixture: ComponentFixture<LastTransactionsComponent>;
  let userService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LastTransactionsComponent],
      providers:[
        {provide:AuthService,useClass:MockUserService}
      ],
      imports: [HttpClientModule, HttpClientTestingModule],
    });
    fixture = TestBed.createComponent(LastTransactionsComponent);
    userService = TestBed.inject(AuthService);
    component = fixture.componentInstance;
    
  });

  it('should not have user defined', () => {
    expect(component.user).toEqual({}as User);
  });
  it('should have mocked user', () => {
    expect(component.user).toBeDefined();
    const changes: SimpleChanges = {
      user: new SimpleChange(null, { id: "1", fullName: 'Test', credentialNumber: "39393939", credentialType: CredentialType.DNI }, true)
    };
    component.ngOnChanges(changes);
    expect(component.user.fullName).toEqual('Test');
  });
});

class MockUserService extends AuthService{
  
  user:User = {id:"1", fullName: 'Test User',credentialNumber:"39393939",credentialType:CredentialType.DNI};
}