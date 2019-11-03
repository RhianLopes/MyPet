package com.mypet.MyPet.security;

import com.mypet.MyPet.domain.User;
import com.mypet.MyPet.dao.UserDAO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

/*
* Service para obter dados do usuário no contexto de autenticação
*/
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserDAO userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User usuario = getUser(() -> (Optional<User>) userRepository.findByEmail(username));
        return UserPrincipal.create(usuario);
    }

    private User getUser(Supplier<Optional<User>> supplier) {
        return supplier.get().orElseThrow(() ->
            new UsernameNotFoundException("Usuário não cadastrado")
        );
    }
}
