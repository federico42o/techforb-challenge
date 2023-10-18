import { Component, Input, OnChanges, OnInit, SimpleChanges, inject } from '@angular/core';
import { TransactionsService } from '../../services/transactions.service';
import { User } from 'src/app/models/user';
import { Page } from 'src/app/models/page';
import { Transaction } from 'src/app/models/transaction';
import { HttpResponseBase } from '@angular/common/http';

@Component({
  selector: 'app-last-transactions',
  templateUrl: './last-transactions.component.html',
  styleUrls: ['./last-transactions.component.css']
})
export class LastTransactionsComponent implements OnInit,OnChanges {

  @Input() user!:User;
  private transactionService = inject(TransactionsService)
  transactions!:Transaction[];
  
  ngOnInit(): void {

  }

  page=0;
  size=3;
  totalElements!:number;
  totalPages!:number;

  icon = {
    TRANSFER:"/assets/icons/arrow-down.png",
    WITHDRAW:"/assets/icons/arrow-down.png",
    DEPOSIT:"/assets/icons/arrow-up.png",
    PAYMENT:"/assets/icons/arrow-down.png"
  }
  statusColor = {
    COMPLETED:"#76c1a1",
    PENDING:"#E5d257",
    CANCELLED:"#C1768C"
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(changes['user'].currentValue){
      const user = changes['user'].currentValue
      this.fetchTransactions(user.id,this.page,this.size);
    }
  }
  fetchTransactions(id:string,page:number,size:number){
    this.transactionService.getAllByClientId(id,page,size).subscribe({
      next:(response:Page<Transaction>)=>{
        this.transactions = response.content
        this.totalElements = response.totalElements
        this.totalPages = response.totalPages
      }
    })
  }

  private pagination(currentPage:number){
    this.page = currentPage;
    this.fetchTransactions(this.user.id,this.page,this.size);
  }

  prevPage(){
    this.page = this.page > 0? --this.page: this.page = 0;
    this.pagination(this.page);
  }

  nextPage(){
    this.page = this.page < this.totalPages-1 ? ++this.page : this.totalPages-1
    this.pagination(this.page);
  }
}
