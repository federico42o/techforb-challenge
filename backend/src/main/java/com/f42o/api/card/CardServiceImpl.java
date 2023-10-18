package com.f42o.api.card;

import com.f42o.api.account.BankAccount;
import com.f42o.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService{

    private final CardRepository repository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createCard(Card card) {
        if(card!=null){
            repository.save(card);
        }
    }

    @Override
    public List<CardDTO> getAllByClientId(Long id) {

        userRepository.findById(id).orElseThrow(
                ()->new RuntimeException("User doesn't exist.")
        );
        List<CardDTO> response = new ArrayList<>();
        repository.findAllByBankAccountClientId(id).forEach(
                card->{
                    response.add(modelMapper.map(card,CardDTO.class));
                }
        );
        return response;
    }
}
