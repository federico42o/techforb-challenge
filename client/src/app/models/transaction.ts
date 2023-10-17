import { BankAccountDto } from "./bank-account-dto"
import { TransactionStatus } from "./transaction-status"
import { TransactionType } from "./transaction-type"

export interface Transaction {
    transactionType:TransactionType,
    amount:number,
    sourceAccount?:BankAccountDto,
    destinationAccount?:BankAccountDto,
    status:TransactionStatus,
    timestamp:Date

}


