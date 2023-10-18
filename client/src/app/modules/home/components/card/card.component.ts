import { Component, Inject, Input, OnChanges, OnInit, SimpleChanges, inject } from '@angular/core';
import { User } from 'src/app/models/user';
import { CardService } from '../../services/card.service';
import { Card } from 'src/app/models/card';
import { BankAccount } from 'src/app/models/bank-account';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnChanges{

  
  @Input() user!:User;
  @Input() bankAccount!:BankAccount;
  private cardService = inject(CardService);
  cards!:Card[];
  favCard!:Card;

  ngOnChanges(changes: SimpleChanges): void {
    if(changes['user'] && changes['user'].currentValue){
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
