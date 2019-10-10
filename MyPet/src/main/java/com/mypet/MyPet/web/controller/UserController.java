package com.mypet.MyPet.web.controller;

import com.mypet.MyPet.domain.User;
import com.mypet.MyPet.repository.UserRepository;
import com.mypet.MyPet.repository.UserRepository2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("public/api/api")
public class UserController {

    private UserRepository userRepository;
    private UserRepository2 userRepository2 = new UserRepository2();

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody User user){
        userRepository2.insert(user);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable("id") Long id){
        userRepository.delete(id);
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@RequestBody User user){
        userRepository.update(user);
    }

    @GetMapping("/find-all")
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll(){
        return userRepository.findAll();
    }


}
