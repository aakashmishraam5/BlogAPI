package com.springboot.blog.security;

import com.springboot.blog.entity.User;
import com.springboot.blog.repository.UserRepostory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepostory userRepostory;

    public CustomUserDetailsService(UserRepostory userRepostory) {
        this.userRepostory = userRepostory;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
      User user= userRepostory.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail)
                .orElseThrow(()->
                        new UsernameNotFoundException("User not found with username or email" + usernameOrEmail));
      Set<GrantedAuthority> authoritySet=user
              .getRoles()
              .stream()
              .map((role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authoritySet);
    }
}
