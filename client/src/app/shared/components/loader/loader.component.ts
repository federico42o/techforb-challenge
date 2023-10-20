import { Component, OnInit, inject } from '@angular/core';
import { LoaderService } from '../../services/loader.service';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-loader',
  templateUrl: './loader.component.html',
  styleUrls: ['./loader.component.css']
})
export class LoaderComponent{

  
  private loaderService = inject(LoaderService)
  isLoading: BehaviorSubject<boolean> = this.loaderService.isLoading$;

}
