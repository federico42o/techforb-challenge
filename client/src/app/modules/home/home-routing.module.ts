import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home.component';
import { CardsComponent } from './pages/cards/cards.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { OperationsComponent } from './pages/operations/operations.component';
import { AuthGuard } from '../auth/auth.guard';

const routes: Routes = [
  {path:"",
  component:HomeComponent,
  canActivate:[AuthGuard],
  children:[
    {path:"inicio",component:DashboardComponent},
    {path:"tarjetas",component:CardsComponent},
    {path:"operaciones",component:OperationsComponent},
    {path:"ayuda",component:DashboardComponent},
  ]
},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
