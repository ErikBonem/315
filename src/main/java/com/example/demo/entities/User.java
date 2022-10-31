package com.example.demo.entities;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;
    @Column
    private Long age;
    @Column
    private String password;
    @Column
    private String email;

    @ManyToMany
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(age, user.age) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(roles, user.roles);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() { return false;}

    @Override
    public boolean isAccountNonLocked() { return false;}

    @Override
    public boolean isCredentialsNonExpired() { return false;}

    @Override
    public boolean isEnabled() { return false;}

    public User(String username, Long age, String password, String email, Collection<Role> roles) {
        this.username = username;
        this.age = age;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public String getRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return String.join(" ", AuthorityUtils.authorityListToSet(getRoles()));
    }
    public Collection<Role> getRoles() {
        return roles;
    }

    public User() {
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
