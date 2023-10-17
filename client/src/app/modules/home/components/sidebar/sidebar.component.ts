import { Component, OnInit, inject } from '@angular/core';
import { MenuService } from '../../services/menu.service';
import { MenuItem } from 'src/app/models/menu-item';
import { AuthService } from 'src/app/modules/auth/services';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  
  private menuService = inject(MenuService);
  private authService = inject(AuthService);
  menuItems!:MenuItem[];  
    ngOnInit(): void {

      this.menuService.getAll().subscribe({
        next:(response:MenuItem[])=>{
          this.menuItems = response;
          this.menuService.setMenuItems(response);
        }
      });
    }
    public logout(){
      this.authService.logout();
    }
}
