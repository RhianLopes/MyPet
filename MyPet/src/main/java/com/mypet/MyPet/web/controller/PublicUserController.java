package com.mypet.MyPet.web.controller;

import com.mypet.MyPet.domain.User;
import com.mypet.MyPet.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/api/user")
public class PublicUserController {

    private UserRepository userRepository = new UserRepository();

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Object insert(@RequestBody User user){
        return userRepository.insert(user);
    }
}
