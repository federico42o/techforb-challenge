package com.f42o.api.transaction;

import com.f42o.api.account.AccountRepository;
import com.f42o.api.account.BankAccount;
import com.f42o.api.account.BankAccountDTO;
import com.f42o.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void withdraw(WithdrawDTO dto) {
        BankAccount source = accountRepository.findByCBU(dto.getCbu())
                .orElseThrow(
                        ()->new RuntimeException("The account doesn't exist.")
                );
        if (!dto.getClientId().equals(source.getClient().getId())) {
            throw new RuntimeException("The specified account doesn't belong to this user.");
        }
        BigDecimal amount = BigDecimal.valueOf(dto.getAmount());
        Transaction withdrawTransaction = new Transaction(TransactionType.WITHDRAW);
        withdrawTransaction.setAmount(amount);
        withdrawTransaction.setSourceAccount(source);
        withdrawTransaction.executeTransaction();
        transactionRepository.save(withdrawTransaction);
        accountRepository.save(source);
    }

    @Transactional
    @Override
    public void deposit(DepositDTO dto) {
        BankAccount destination = accountRepository.findByCBU(dto.getDestinationCbu()).orElseThrow(
                ()->new RuntimeException("The account doesn't exist.")
        );


        BigDecimal amount = BigDecimal.valueOf(dto.getAmount());
        Transaction depositTransaction = new Transaction(TransactionType.DEPOSIT);
        depositTransaction.setAmount(amount);
        depositTransaction.setDestinationAccount(destination);
        depositTransaction.executeTransaction();
        accountRepository.save(destination);
        transactionRepository.save(depositTransaction);


    }

    @Transactional
    @Override
    public void transfer(TransferDTO dto) {
        BankAccount destination = accountRepository.findByCBU(dto.getDestinationCbu()).orElseThrow(
                ()->new RuntimeException("The account doesn't exist.")
        );
        BankAccount source = accountRepository.findByCBU(dto.getSourceCbu())
                .orElseThrow(
                        ()->new RuntimeException("The account doesn't exist.")
                );
        if (!dto.getClientId().equals(source.getClient().getId())) {
            throw new RuntimeException("The specified account doesn't belong to this user.");
        }
        BigDecimal amount = BigDecimal.valueOf(dto.getAmount());
        Transaction transferTransaction = new Transaction(TransactionType.TRANSFER);
        transferTransaction.setSourceAccount(source);
        transferTransaction.setDestinationAccount(destination);
        transferTransaction.setAmount(amount);
        transferTransaction.executeTransaction();
        transferTransaction.setClient(source.getClient());
        accountRepository.save(source);
        accountRepository.save(destination);
        transactionRepository.save(transferTransaction);


    }

    @Override
    public Page<TransactionDTO> getAllByClientId(Pageable pageable, Long clientId) {
        userRepository.findById(clientId).orElseThrow(
                ()->new RuntimeException("User doesn't exist.")
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


    private BankAccountDTO buildDto(BankAccount entity){
        return BankAccountDTO.builder()
                .fullName(entity.getClient().getFullName())
                .cbu(entity.getCBU())
                .build();
    }
}
