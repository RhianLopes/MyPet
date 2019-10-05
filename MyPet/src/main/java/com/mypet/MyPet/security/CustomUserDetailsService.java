//package com.mypet.MyPet.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//import java.util.function.Supplier;
//
///*
//* Service para obter dados do usuário no contexto de autenticação
//*/
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UsuarioRepository usuarioRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) {
//        Usuario usuario = getUser(() -> usuarioRepository.findByEmail(username));
//        return UserPrincipal.create(usuario);
//    }
//
//    public UserDetails loadUserById(Long id) {
//        Usuario usuario = getUser(() -> usuarioRepository.findById(id));
//        return UserPrincipal.create(usuario);
//    }
//
//    private Usuario getUser(Supplier<Optional<Usuario>> supplier) {
//        return supplier.get().orElseThrow(() ->
//            new UsernameNotFoundException("Usuário não cadastrado")
//        );
//    }
//}
