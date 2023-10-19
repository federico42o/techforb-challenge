package com.f42o.api.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository repository;


    public List<MenuItem> getAll(){
        return repository.findAll();
    }

    public void save(MenuItem menuItem){
        repository.save(menuItem);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

}
