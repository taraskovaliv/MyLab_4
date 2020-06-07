package com.kovaliv.lab4.security.services.impl;

import com.kovaliv.lab4.constants.ErrorConstants;
import com.kovaliv.lab4.dtos.AddUserToBlackListDto;
import com.kovaliv.lab4.entities.enums.PaidStatus;
import com.kovaliv.lab4.security.dtos.AddUserRequestDto;
import com.kovaliv.lab4.security.dtos.LoginDto;
import com.kovaliv.lab4.security.dtos.UserDto;
import com.kovaliv.lab4.security.entities.Role;
import com.kovaliv.lab4.security.entities.User;
import com.kovaliv.lab4.security.jwt.JwtUtils;
import com.kovaliv.lab4.security.repository.RoleRepository;
import com.kovaliv.lab4.security.repository.UserRepository;
import com.kovaliv.lab4.security.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserDto save(AddUserRequestDto addUserRequestDto) {
        try {
            findByUsername(addUserRequestDto.getUsername());
        } catch (UsernameNotFoundException e) {

            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("admin"));

            User user = User.builder()
                    .password(passwordEncoder.encode(addUserRequestDto.getPassword()))
                    .username(addUserRequestDto.getUsername())
                    .roles(roles)
                    .build();

            return modelMapper.map(userRepository.save(user), UserDto.class);
        }
        throw new BadCredentialsException(ErrorConstants.USERNAME_IS_ALREADY_USED);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(ErrorConstants.USER_NOT_FOUND_BY_USERNAME + username));
    }

    @Override
    public UserDto authenticateUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return UserDto.builder()
                .token(jwt)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .password(userDetails.getPassword())
                .build();
    }

    @Override
    public String addToBlackList(AddUserToBlackListDto addUserToBlackListDto) {
        User user = findByUsername(addUserToBlackListDto.getUsername());

        if (!hasNotPaidOrders(user)) {
            throw new RuntimeException(ErrorConstants.USER_HAVE_NO_NOT_PAID_ORDERS);
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("black_list_user"));
        user.setRoles(roles);
        userRepository.save(user);

        return "Successfully blacklisted user " + user.getUsername();
    }

    private boolean hasNotPaidOrders(User user) {
        if (user.getOrders()
                .stream().noneMatch(order -> order.getPaidStatus() == PaidStatus.NOT_PAID)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}