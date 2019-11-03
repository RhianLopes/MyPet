package com.mypet.MyPet.web.controller;

import com.mypet.MyPet.dao.UserDAO;
import com.mypet.MyPet.security.AuthenticationService;
import com.mypet.MyPet.web.request.LoginRequest;
import com.mypet.MyPet.web.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/public/authentication")
public class AuthenticationController {

    private UserDAO userDAO = new UserDAO();

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        String username = request.getEmail();
        String password = request.getPassword();
        return authenticationService.authenticate(username, password);
    }

    @GetMapping("/verificacoes")
    @ResponseStatus(HttpStatus.OK)
    public boolean verificacao(@RequestParam("email") String email){
        return userDAO.findByEmailExists(email);
    }
}
