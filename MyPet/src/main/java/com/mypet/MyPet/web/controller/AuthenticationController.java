package com.mypet.MyPet.web.controller;

import com.mypet.MyPet.repository.UserRepository;
import com.mypet.MyPet.security.AuthenticationService;
import com.mypet.MyPet.web.controller.request.LoginRequest;
import com.mypet.MyPet.web.controller.response.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/public/authentication")
public class AuthenticationController {

    private UserRepository userRepository = new UserRepository();

    private AuthenticationService authenticationService = new AuthenticationService();

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
        return userRepository.findByEmailExists(email);
    }
}
