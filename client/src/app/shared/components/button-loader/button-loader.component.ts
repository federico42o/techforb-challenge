import { Component, Input, inject } from '@angular/core';
import { LoaderService } from '../../services/loader.service';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-button-loader',
  templateUrl: './button-loader.component.html',
  styleUrls: ['./button-loader.component.css']
})
export class ButtonLoaderComponent {

  @Input() type:string="submit";
  @Input() label:string="Button";

  private loaderService = inject(LoaderService)
  isLoading: BehaviorSubject<boolean> = this.loaderService.isLoading$;

}
