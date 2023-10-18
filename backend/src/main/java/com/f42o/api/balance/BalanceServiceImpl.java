package com.f42o.api.balance;

import com.f42o.api.account.AccountRepository;
import com.f42o.api.account.BankAccount;
import com.f42o.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceServiceImpl implements BalanceService{
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final BalanceSnapshotRepository snapshotRepository;




    @Override
    public void snapshotBalance(BankAccount bankAccount){
        snapshotRepository.save(BalanceSnapshot.builder().bankAccount(bankAccount).balance(bankAccount.getBalance()).build());
    }

    @Override
    public BalanceDTO getBalanceByClientIdBetween(Long id,LocalDateTime start,LocalDateTime end,BalanceType balanceType) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime startDate = LocalDateTime.parse(start.toString(), formatter);
        LocalDateTime endDate = LocalDateTime.parse(end.toString(), formatter);
        System.out.println("days: "+Duration.between(startDate,endDate).toDays());

        userRepository.findById(id).orElseThrow(
                ()->new RuntimeException("The user doesn't exist whit id " + id)
        );
        BankAccount bankAccount = accountRepository.findByClientId(id).orElseThrow(
                ()->new RuntimeException("This user doesn't have an active account.")
        );
        return getBalancesOn(bankAccount,startDate,endDate,balanceType);
    }



    public BalanceDTO getBalancesOn(BankAccount bankAccount, LocalDateTime startDate, LocalDateTime endDate,BalanceType balanceType){
        List<BalanceSnapshot> snapshots = snapshotRepository.findAllByBankAccountIdAndTimestampBetweenOrderByTimestampAsc(bankAccount.getId(),startDate,endDate);
        if(balanceType == BalanceType.WEEKLY && Duration.between(startDate,endDate).toDays()>7){
            throw new RuntimeException("The operation could not be completed because the maximum days for this operation have been exceeded.");
        }
        if(balanceType == BalanceType.MONTHLY && Duration.between(startDate,endDate).toDays()>365){
            throw new RuntimeException("The operation could not be completed because the maximum days for this operation have been exceeded.");
        }
        if(balanceType == BalanceType.DAILY && Duration.between(startDate,endDate).toDays()>1){
            throw new RuntimeException("The operation could not be completed because the maximum days for this operation have been exceeded.");
        }


        BalanceDTO balanceDTO = BalanceDTO.getBalanceDTO(balanceType);
        if(balanceType == BalanceType.DAILY){
            BalanceSnapshot lastSnapshotOfTheDay = snapshots.get(snapshots.size()-1);
            balanceDTO.getLabels()[0] = lastSnapshotOfTheDay.getTimestamp().getDayOfWeek().toString();
            balanceDTO.getValues()[0] = lastSnapshotOfTheDay.getBalance();
        }
        for (BalanceSnapshot snapshot : snapshots) {
            if (balanceType == BalanceType.WEEKLY) {
                balanceDTO.getValues()[snapshot.getTimestamp().getDayOfWeek().getValue() - 1] = snapshot.getBalance();
            }
            if (balanceType == BalanceType.MONTHLY) {
                balanceDTO.getValues()[snapshot.getTimestamp().getMonthValue() - 1] = snapshot.getBalance();
            }

        }

        return balanceDTO;
    }

}
