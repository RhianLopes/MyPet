package com.mypet.MyPet.security;

import com.mypet.MyPet.domain.User;
import com.mypet.MyPet.dao.UserDAO;
import com.mypet.MyPet.web.response.LoginResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/*
* Service para iniciar um novo processo de autenticação
* */

@Service
public class AuthenticationService {

    private static final String HEADER_PREFIX = "Bearer ";

    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);

    private UserDAO userRepository = new UserDAO();

    public LoginResponse authenticate(String username, String password) {

        User user = (User) userRepository.findByEmail(username);
        if (!encoder.matches(password, user.getPassword())){
            return null;
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new LoginResponse(HEADER_PREFIX + jwtTokenProvider.generateToken(authentication, username));
    }
}
