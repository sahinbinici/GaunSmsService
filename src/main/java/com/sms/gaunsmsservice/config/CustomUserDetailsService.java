package com.sms.gaunsmsservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.user.username}")
    private String username; // Kullanıcı adını application.properties'ten al

    @Value("${app.user.password}")
    private String password; // Şifreyi application.properties'ten al

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Kullanıcı bilgilerini application.properties'ten al
        if (this.username.equals(username)) {
            return new User(this.username, passwordEncoder.encode(this.password), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Kullanıcı bulunamadı");
        }
    }
} 