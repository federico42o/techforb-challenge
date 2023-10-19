import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login';
import { NoLoginGuard } from './no-login.guard';
import { AuthComponent } from './auth.component';
import { RegisterComponent } from './pages/register/register.component';

const routes: Routes = [
{path:'',
canActivate:[NoLoginGuard],

component:AuthComponent,
  children:[
    {path:"login",component:LoginComponent},
    {path:"register",component:RegisterComponent}
  ]
},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
