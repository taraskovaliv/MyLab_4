package com.kovaliv.lab4.security.services.impl;

import com.kovaliv.lab4.constants.ErrorConstants;
import com.kovaliv.lab4.security.entities.User;
import com.kovaliv.lab4.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(ErrorConstants.USER_NOT_FOUND_BY_USERNAME + username));

        return UserDetailsImpl.build(user);
    }
}
