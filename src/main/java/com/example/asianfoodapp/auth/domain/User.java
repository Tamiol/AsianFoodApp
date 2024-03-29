package com.example.asianfoodapp.auth.domain;

import com.example.asianfoodapp.catalog.domain.Recipe;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Setter
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "users_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "users_id_seq", sequenceName = "USERS_ID_SEQ", allocationSize = 1)
    private Long id;
    private String uuid;
    @Column(unique = true, nullable = false)
    private String login;
    @Getter
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Getter
    private Role role;
    @Column(name = "islock")
    private boolean isLock = false;
    @Column(name = "isenabled")
    private boolean isEnabled = true;
    @OneToMany
    @JsonIgnoreProperties("author")
    private Set<Recipe> recipes = new HashSet<>();

    public User(String login, String email, String password, Role role) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
        generateUuid();
    }

    public User() {
        generateUuid();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLock;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    private void generateUuid() {
        setUuid(UUID.randomUUID().toString());
    }

}
