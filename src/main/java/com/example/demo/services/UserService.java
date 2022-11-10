package com.example.demo.services;

import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepo userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Transactional
    public void save(User newuser, Long[] roles) {
        Set<Role> roleSet = new HashSet<>();
        for (Long s : roles) {
            roleSet.add(roleRepository.getById(s));
        }
        newuser.setRoles(roleSet);
        userRepository.save(newuser);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    public void update(User user, Long[] roles) {
        Set<Role> roleSet = new HashSet<>();
        for (Long s : roles) {
            roleSet.add(roleRepository.getById(s));
        }
        user.setRoles(roleSet);
        userRepository.update(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }



    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName());
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
