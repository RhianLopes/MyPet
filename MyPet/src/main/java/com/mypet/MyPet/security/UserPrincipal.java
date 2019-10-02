//package com.mypet.MyPet.security;
//
//import br.com.cwi.crescer.api.domain.Preferencia;
//import br.com.cwi.crescer.api.domain.Usuario;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//
//
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@EqualsAndHashCode(of = "id")
//public class UserPrincipal implements UserDetails {
//
//    private Long id;
//
//    private String nome;
//
//    private String email;
//
//    private String foto;
//
//    @JsonIgnore
//    private String senha;
//
//    private BigDecimal latitude;
//
//    private BigDecimal longitude;
//
//    private Preferencia preferencia;
//
//    private boolean ativo;
//
//    private LocalDate dataNascimento;
//
//    private Collection<? extends GrantedAuthority> authorities;
//
//    public UserPrincipal(Long id, String nome, String email, String foto, String senha,
//                         boolean ativo, BigDecimal longitude, BigDecimal latitude, Preferencia preferencia,
//                         Collection<? extends GrantedAuthority> authorities) {
//        this.id = id;
//        this.nome = nome;
//        this.email = email;
//        this.foto = foto;
//        this.senha = senha;
//        this.longitude = longitude;
//        this.latitude = latitude;
//        this.preferencia = preferencia;
//        this.ativo = ativo;
//        this.authorities = authorities;
//    }
//
//    public static UserPrincipal create(Usuario usuario) {
//
//        List<GrantedAuthority> authorities = Arrays.asList(
//                new SimpleGrantedAuthority(usuario.getPerfil().getRole())
//        );
//
//        return new UserPrincipal(
//                usuario.getId(),
//                usuario.getNome(),
//                usuario.getEmail(),
//                usuario.getFoto(),
//                usuario.getSenha(),
//                usuario.isAtivo(),
//                usuario.getLongitude(),
//                usuario.getLatitude(),
//                usuario.getPreferencia(),
//                authorities
//        );
//    }
//
//    @Override
//    @JsonIgnore
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    @JsonIgnore
//    public String getPassword() {
//        return senha;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return ativo;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return ativo;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return ativo;
//    }
//
//}