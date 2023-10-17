import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login';

const routes: Routes = [
{path:"auth",
  children:[
    {path:"login",component:LoginComponent},
    {path:"register",component:LoginComponent}
  ]
},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
