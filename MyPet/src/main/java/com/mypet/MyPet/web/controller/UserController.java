package com.mypet.MyPet.web.controller;

import com.mypet.MyPet.domain.User;
import com.mypet.MyPet.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("public/api/user")
public class UserController {

    private UserRepository userRepository = new UserRepository();

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Object insert(@RequestBody User user){
        return userRepository.insert(user);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable("id") Long id){
        userRepository.delete(id);
    }

    @PutMapping("/inactivate/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void inactivate(@PathVariable("id") Long id){
        userRepository.inactivate(id);
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Object update(@RequestBody User user){
        return userRepository.update(user);
    }

    @GetMapping("/find-all")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Object> findAll(){
        return userRepository.findAll();
    }

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object find(@PathVariable("id") Long id){
        return userRepository.find(id);
    }
}
