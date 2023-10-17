import { Component, OnInit, inject } from '@angular/core';
import { AuthService } from '../auth/services';
import { User } from 'src/app/models/user';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  
  user!:User;
  private authService = inject(AuthService);
  
  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe({
      next:(user:User | null)=>{

        if(user){
          this.user = user;
        }
      }
    })

  }


  public logout(){
    this.authService.logout();
  }
}
