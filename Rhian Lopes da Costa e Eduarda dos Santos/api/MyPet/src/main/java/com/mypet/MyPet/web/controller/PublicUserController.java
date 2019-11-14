package com.mypet.MyPet.web.controller;

import com.mypet.MyPet.domain.User;
import com.mypet.MyPet.dao.UserDAO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/api/user")
public class PublicUserController {

    private UserDAO userDAO = new UserDAO();

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User insert(@RequestBody User user){
        return (User) userDAO.insert(user);
    }
}
