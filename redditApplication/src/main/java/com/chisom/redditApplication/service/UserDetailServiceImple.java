package com.chisom.redditApplication.service;

import com.chisom.redditApplication.model.Users;
import com.chisom.redditApplication.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailServiceImple implements UserDetailsService {

    private final UsersRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Optional<Users> UserOptional = userRepository.findByUsername(username);
        Users users = UserOptional.orElseThrow(() -> new UsernameNotFoundException("No User " +
                "Found with username : " + username));
        return new org.springframework.security
                .core.userdetails.User(users.getUsername(), users.getPassword(),
                users.isEnabled(), true, true,
                true, getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
