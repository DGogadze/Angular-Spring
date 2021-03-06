package com.angular.spring.security;

import com.angular.spring.entities.User;
import com.angular.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return new SecurityUser(user);
        } catch (Exception e) {
            return null;
        }
    }

    public User findByUsername(String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        return userOptional.orElse(null);
    }
}
