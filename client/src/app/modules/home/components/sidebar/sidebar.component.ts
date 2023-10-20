import { Component, HostListener, OnInit, inject } from '@angular/core';
import { MenuService } from '../../services/menu.service';
import { MenuItem } from 'src/app/models/menu-item';
import { AuthService } from 'src/app/modules/auth/services';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  
  private menuService = inject(MenuService);
  private authService = inject(AuthService);

  showMenu=false
  isSmallScreen = false;
  router = inject(Router);
  menuItems!:MenuItem[];
  menuItemsMap: { [key: string]: MenuItem } = {}  
    ngOnInit(): void {

      this.menuService.getAll().subscribe({
        next:(response:MenuItem[])=>{
          response.forEach(
            item=>{
              this.menuItemsMap[item.title.toUpperCase()] = item; 
            }
          );

          this.menuItems = [this.menuItemsMap["INICIO"],this.menuItemsMap["TARJETAS"],this.menuItemsMap["OPERACIONES"],this.menuItemsMap["AYUDA"]]
        }
      });
      this.checkScreenSize();
    }
    public logout(){
      this.authService.logout();
      this.router.navigate(['/auth/login'])
    }


    toggleMenu(){
      this.showMenu = !this.showMenu
    }
    

    checkScreenSize() {
      this.isSmallScreen = window.innerWidth <= 600;
      this.showMenu = !this.isSmallScreen
    }
  
    @HostListener('window:resize', ['$event'])
    onResize(event: any) {
      this.checkScreenSize();
    }

}
