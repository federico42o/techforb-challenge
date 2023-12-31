package com.f42o.api.transaction;

import com.f42o.api.account.AccountRepository;
import com.f42o.api.account.BankAccount;
import com.f42o.api.account.BankAccountDTO;
import com.f42o.api.balance.BalanceService;
import com.f42o.api.exception.BankAccountNotFoundException;
import com.f42o.api.exception.UserNotFoundException;
import com.f42o.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final BalanceService balanceService;

    @Transactional
    @Override
    public void withdraw(WithdrawDTO dto) {
        BankAccount source = accountRepository.findByCBU(dto.getCbu())
                .orElseThrow(
                        BankAccountNotFoundException::new
                );
        if (!dto.getClientId().equals(source.getClient().getId())) {
            throw new RuntimeException("The specified account doesn't belong to this user.");
        }
        BigDecimal amount = dto.getAmount();
        Transaction withdrawTransaction = new Transaction(TransactionType.WITHDRAW);
        withdrawTransaction.setAmount(amount);
        withdrawTransaction.setSourceAccount(source);
        withdrawTransaction.executeTransaction();
        withdrawTransaction.setClient(source.getClient());
        transactionRepository.save(withdrawTransaction);
        BankAccount updatedAccount = accountRepository.save(source);
        balanceService.snapshotBalance(updatedAccount);

    }

    @Transactional
    @Override
    public void deposit(DepositDTO dto) {
        BankAccount destination = accountRepository.findByCBU(dto.getDestinationCbu())
                .orElseThrow(
                BankAccountNotFoundException::new
        );


        BigDecimal amount = dto.getAmount();
        Transaction depositTransaction = new Transaction(TransactionType.DEPOSIT);
        depositTransaction.setAmount(amount);
        depositTransaction.setDestinationAccount(destination);
        depositTransaction.executeTransaction();
        depositTransaction.setClient(destination.getClient());
        BankAccount updatedAccount = accountRepository.save(destination);
        transactionRepository.save(depositTransaction);
        balanceService.snapshotBalance(updatedAccount);


    }

    @Transactional
    @Override
    public void transfer(TransferDTO dto) {
        BankAccount destination = accountRepository.findByCBU(dto.getDestinationCbu())
                .orElseThrow(
                BankAccountNotFoundException::new
        );
        BankAccount source = accountRepository.findByCBU(dto.getSourceCbu())
                .orElseThrow(
                        BankAccountNotFoundException::new
                );
        if (!dto.getClientId().equals(source.getClient().getId())) {
            throw new RuntimeException("The specified account doesn't belong to this user.");
        }
        BigDecimal amount = dto.getAmount();
        Transaction transferTransaction = new Transaction(TransactionType.TRANSFER);
        transferTransaction.setSourceAccount(source);
        transferTransaction.setDestinationAccount(destination);
        transferTransaction.setAmount(amount);
        transferTransaction.executeTransaction();
        transferTransaction.setClient(source.getClient());
        BankAccount updatedSrcAccount = accountRepository.save(source);
        BankAccount updatedAccount = accountRepository.save(destination);
        transactionRepository.save(transferTransaction);
        balanceService.snapshotBalance(updatedSrcAccount);
        balanceService.snapshotBalance(updatedAccount);


    }

    @Override
    public Page<TransactionDTO> getAllByClientId(Pageable pageable, Long clientId) {
        userRepository.findById(clientId).orElseThrow(
                ()->new UserNotFoundException("User doesn't exist with id "+clientId)
        );
        Page<Transaction> transactions = transactionRepository.findAllByClientId(pageable,clientId);
        List<TransactionDTO> response = new ArrayList<>();
        transactions.forEach(
                t-> {
                    TransactionDTO transactionDTO = TransactionDTO.builder()
                            .transactionType(t.getTransactionType())
                            .status(t.getStatus())
                            .timestamp(t.getTimestamp())
                            .amount(t.getAmount())
                            .build();
                    if(t.getSourceAccount()!=null){
                        transactionDTO.setSourceAccount(buildDto(t.getSourceAccount()));
                    }
                    if(t.getDestinationAccount()!=null){
                        transactionDTO.setDestinationAccount(buildDto(t.getDestinationAccount()));
                    }
                    response.add(transactionDTO);
                }
        );
        return new PageImpl<>(response,pageable,transactions.getTotalElements());
    }

    @Override
    public MonthlyTransactionDTO getAllIncomes(Long id) {
        userRepository.findById(id).orElseThrow(
                ()->new UserNotFoundException("User doesn't exist with id "+id)
        );
        BankAccount bankAccount = accountRepository.findByClientId(id).orElseThrow(
                ()->new RuntimeException("The user doesn't have an active account.")
        );

        List<Transaction> incomes = transactionRepository.findAllIncomes(id,bankAccount.getId(),LocalDate.now());
        List<Transaction> incomesPastMonth = transactionRepository.findAllIncomes(id,bankAccount.getId(),LocalDate.now().minusMonths(1));

        return buildDto(incomes,incomesPastMonth,SummaryType.INCOMES);
    }

    @Override
    public MonthlyTransactionDTO getAllExpenses(Long id) {
        userRepository.findById(id).orElseThrow(
                ()->new UserNotFoundException("User doesn't exist with id "+id)
        );
        BankAccount bankAccount = accountRepository.findByClientId(id).orElseThrow(
                ()->new RuntimeException("The user doesn't have an active account.")
        );

        List<Transaction> incomes = transactionRepository.findAllExpenses(id,bankAccount.getId(),LocalDate.now());
        List<Transaction> incomesPastMonth = transactionRepository.findAllExpenses(id,bankAccount.getId(),LocalDate.now().minusMonths(1));

        return buildDto(incomes,incomesPastMonth,SummaryType.EXPENSES);
    }


    private BankAccountDTO buildDto(BankAccount entity){
        return BankAccountDTO.builder()
                .fullName(entity.getClient().getFullName())
                .cbu(entity.getCBU())
                .build();
    }

    public MonthlyTransactionDTO buildDto(List<Transaction> thisMonth, List<Transaction> pastMonth,SummaryType type){
        BigDecimal actualValue=BigDecimal.ZERO;
        BigDecimal pastValue = BigDecimal.ZERO;
        BigDecimal differencePercentage = BigDecimal.ZERO;
        for (Transaction transaction : thisMonth) {
            actualValue = actualValue.add(transaction.getAmount());
        }
        for (Transaction transaction : pastMonth) {
            pastValue = pastValue.add(transaction.getAmount());
        }
        if (!pastValue.equals(BigDecimal.ZERO)) {
            differencePercentage = ((actualValue.subtract(pastValue)).divide(pastValue, 2, RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(100));
        }

        return MonthlyTransactionDTO.builder()
                .total(actualValue)
                .differenceBetweenPastMonth(differencePercentage)
                .type(type)
                .build();
    }
}
