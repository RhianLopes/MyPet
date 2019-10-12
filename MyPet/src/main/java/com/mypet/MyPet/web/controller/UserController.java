package com.mypet.MyPet.web.controller;

import com.mypet.MyPet.domain.User;
import com.mypet.MyPet.repository.UserRepository;
import com.mypet.MyPet.repository.UserRepository2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("public/api/api")
public class UserController {

    private UserRepository userRepository;
    UserRepository2 userRepository2 = new UserRepository2();

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Object register(@RequestBody User user){
        return userRepository2.insert(user);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable("id") Long id){
        userRepository2.delete(id);
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Object update(@RequestBody User user){
        return userRepository2.update(user);
    }

    @GetMapping("/find-all")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Object> findAll(){
        return userRepository2.findAll();
    }


}
