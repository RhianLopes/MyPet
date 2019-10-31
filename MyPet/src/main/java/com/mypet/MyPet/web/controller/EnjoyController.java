package com.mypet.MyPet.web.controller;

import com.mypet.MyPet.domain.Enjoy;
import com.mypet.MyPet.repository.EnjoyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/enjoy")
public class EnjoyController {

    private EnjoyRepository enjoyRepository = new EnjoyRepository();

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Enjoy insert(@RequestBody Enjoy enjoy){
        return (Enjoy) enjoyRepository.insert(enjoy);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable("id") Long id){
        enjoyRepository.delete(id);
    }

    @PutMapping("/inactivate/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void inactivate(@PathVariable("id") Long id){
        enjoyRepository.inactivate(id);
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Enjoy update(@RequestBody Enjoy enjoy){
        return (Enjoy) enjoyRepository.update(enjoy);
    }

    @GetMapping("/find-all")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Enjoy> findAll(){
        return enjoyRepository.findAll();
    }

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Enjoy find(@PathVariable("id") Long id){
        return (Enjoy) enjoyRepository.findById(id);
    }
}
