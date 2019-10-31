package com.mypet.MyPet.web.controller;

import com.mypet.MyPet.domain.User;
import com.mypet.MyPet.repository.UserRepository;
import com.mypet.MyPet.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserRepository<User> userRepository = new UserRepository<User>();

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
    public User update(@RequestBody User user){
        return (User) userRepository.update(user);
    }

    @GetMapping("/find-all")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<User> findAll(){
        return userRepository.findAll();
    }

    @GetMapping("/find-by-id")
    @ResponseStatus(HttpStatus.OK)
    public User find(@AuthenticationPrincipal UserPrincipal currentUser){
        return (User) userRepository.findById(currentUser.getId());
    }

    @GetMapping("/find-by-email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public User find(@PathVariable("email") String email)   {
        return userRepository.findByEmail(email);
    }
}
