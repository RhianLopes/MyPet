package com.mypet.MyPet.web.controller;

import com.mypet.MyPet.domain.Pet;
import com.mypet.MyPet.domain.User;
import com.mypet.MyPet.repository.PetRepository;
import com.mypet.MyPet.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/pet")
public class PetController {

    private PetRepository petRepository = new PetRepository();

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Object insert(@RequestBody Pet pet, @AuthenticationPrincipal UserPrincipal currentUser){
        pet.setUser(new User());
        pet.getUser().setId(currentUser.getId());
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

    @GetMapping("/find-by-user")
    @ResponseStatus(HttpStatus.OK)
    public Object findByUserId(@AuthenticationPrincipal UserPrincipal currentUser){
        return petRepository.findAllByUserId(currentUser.getId());
    }
}
