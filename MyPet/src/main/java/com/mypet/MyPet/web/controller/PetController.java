package com.mypet.MyPet.web.controller;

import com.mypet.MyPet.domain.Pet;
import com.mypet.MyPet.repository.GenericRepository;
import com.mypet.MyPet.repository.PetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/pet")
public class PetController {

    private PetRepository petRepository = new PetRepository();

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Object insert(@RequestBody Pet pet){
        return petRepository.insert(pet);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable("id") Long id){
        petRepository.delete(id);
    }

    @PutMapping("/inactivate/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void inactivate(@PathVariable("id") Long id){
        petRepository.inactivate(id);
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Object update(@RequestBody Pet pet){
        return petRepository.update(pet);
    }

    @GetMapping("/find-all")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Object> findAll(){
        return petRepository.findAll();
    }

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object find(@PathVariable("id") Long id){
        return petRepository.findById(id);
    }
    @GetMapping("/find-by-user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Object findByUserId(@PathVariable("userId") Long id){
        return petRepository.findAllByUserId(id);
    }
}
