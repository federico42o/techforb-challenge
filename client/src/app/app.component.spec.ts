import { TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { CookieService } from 'ngx-cookie-service';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from './modules/auth/auth.interceptor';
import { LoaderInterceptor } from './shared/services/loader.interceptor';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('AppComponent', () => {
  beforeEach(() => TestBed.configureTestingModule({
    declarations: [
      AppComponent,
    ],
    providers: [
      CookieService,
      {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
      {provide:HTTP_INTERCEPTORS,useClass:LoaderInterceptor,multi:true}
    ],
    imports: [AppRoutingModule,BrowserAnimationsModule]
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'client'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('client');
  });

});
