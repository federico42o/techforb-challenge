import { Component, Inject, Input, OnChanges, OnInit, SimpleChanges, inject } from '@angular/core';
import { User } from 'src/app/models/user';
import { CardService } from '../../services/card.service';
import { Card } from 'src/app/models/card';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit, OnChanges{

  
  @Input() user!:User;
  private cardService = inject(CardService);
  cards!:Card[];
  favCard!:Card;
  ngOnInit(): void {
  }
  ngOnChanges(changes: SimpleChanges): void {
    if(changes['user'].currentValue){
      const user = changes['user'].currentValue
      this.cardService.getByClientId(user.id).subscribe({
          next:(response:Card[])=>{
            this.cards = response;
            this.favCard = response[0]
          }
      });
    }
  }

  formatCardNumber(cardNumber: string): string {
    if (!cardNumber) {
      return '';
    }
    return cardNumber.replace(/(.{4})/g, '$1 ');
  }


}
