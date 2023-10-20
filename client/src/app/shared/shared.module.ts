import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoaderComponent } from './components/loader/loader.component';
import { ButtonLoaderComponent } from './components/button-loader/button-loader.component';


@NgModule({
  declarations: [
    LoaderComponent,
    ButtonLoaderComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports:[
    FormsModule,
    ReactiveFormsModule,
    LoaderComponent,
    ButtonLoaderComponent

  ]
})
export class SharedModule { }
