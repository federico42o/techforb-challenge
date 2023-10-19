import { Component, OnInit, inject } from '@angular/core';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/modules/auth/services';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent implements OnInit {
  
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
