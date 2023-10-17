import { Component, OnInit, inject } from '@angular/core';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/modules/auth/services';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit{
  
  private authService = inject(AuthService)
  user!:User;
  ngOnInit(): void {
      this.authService.getCurrentUser().subscribe({
        next:(response:User|null)=>{
          if(response){

            this.user = response;
          }
        }
      })
  }

}
