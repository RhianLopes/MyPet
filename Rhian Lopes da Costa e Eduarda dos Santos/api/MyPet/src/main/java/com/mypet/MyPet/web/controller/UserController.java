package com.mypet.MyPet.web.controller;

import com.mypet.MyPet.domain.User;
import com.mypet.MyPet.dao.UserDAO;
import com.mypet.MyPet.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserDAO userDAO = new UserDAO();

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable("id") Long id){
        userDAO.delete(id);
    }

    @PutMapping("/inactivate/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void inactivate(@PathVariable("id") Long id){
        userDAO.inactivate(id);
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User update(@RequestBody User object){
        return (User) userDAO.update(object);
    }

    @GetMapping("/find-all")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<User> findAll(){
        return userDAO.findAll();
    }

    @GetMapping("/find-by-id")
    @ResponseStatus(HttpStatus.OK)
    public User find(@AuthenticationPrincipal UserPrincipal currentUser){
        return (User) userDAO.findById(currentUser.getId());
    }

    @GetMapping("/find-by-email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public User find(@PathVariable("email") String email)   {
        return (User) userDAO.findByEmail(email);
    }
}
