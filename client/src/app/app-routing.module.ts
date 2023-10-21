import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NoLoginGuard } from './modules/auth/no-login.guard';
import { AuthGuard } from './modules/auth/auth.guard';
import { ButtonLoaderComponent } from './shared/components/button-loader/button-loader.component';



const routes: Routes = [
  { path: '', redirectTo: '/inicio', pathMatch: 'full' },
  {
    path: 'auth',
    canActivate:[NoLoginGuard],
    loadChildren: () =>
      import('./modules/auth/auth.module').then((m) => m.AuthModule),
  },

  {
    path: '',
    canActivate:[AuthGuard],
    loadChildren: () =>
    import('./modules/home/home.module').then((m) => m.HomeModule),
  },
  

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}