package com.mypet.MyPet.security;

import com.mypet.MyPet.domain.User;
import com.mypet.MyPet.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

import static java.lang.Long.parseLong;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

@Component
public class JwtTokenProvider {

    UserRepository userRepository = new UserRepository();

    // cria token a partir de um usuário autenticado
    public String generateToken(Authentication authentication, String email) {

        User user = (User) userRepository.findByEmail(email);

        Date now = new Date();
        Date expiryDate = new Date(new Date().getTime() + 864000000);

        return Jwts.builder()
            .setSubject(Long.toString(user.getId()))
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, "my-pet-2019-java-jwt")
            .compact();
    }

    // obtém id do usuário a partir de um token
    public Optional<Long> getUserId(String jwt) {
        try {
            Claims claims = parse(jwt).getBody();

            // claims.get(key, class)

            return ofNullable(parseLong(claims.getSubject()));
        } catch (Exception ex) {
            return empty();
        }
    }

    private Jws<Claims> parse(String jwt) {
        return Jwts.parser().setSigningKey("my-pet-2019-java-jwt").parseClaimsJws(jwt);
    }
}
