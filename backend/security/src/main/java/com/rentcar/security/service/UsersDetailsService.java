package com.rentcar.security.service;

import com.rentcar.security.dto.UserDetailsDto;
import com.rentcar.security.entity.User;
import com.rentcar.security.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailsService implements UserDetailsService {
    private final IUserRepository userRepository;

    public UsersDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
