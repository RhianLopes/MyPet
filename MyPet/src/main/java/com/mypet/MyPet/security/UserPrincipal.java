package com.mypet.MyPet.security;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mypet.MyPet.domain.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserPrincipal implements UserDetails {

    private Long id;

    private String name;

    private String nickname;

    private String email;

    private String password;

    private String photo;

    private boolean isActive;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String name, String nickname, String email, String password, String photo,
                         boolean isActive, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.isActive = isActive;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {

        List<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority(user.getProfile().getRole())
        );

        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getNickname(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoto(),
                user.isActive(),
                authorities
        );
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

}