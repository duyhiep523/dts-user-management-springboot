package com.duyhiep523.user_management.services;

import com.duyhiep523.user_management.common.ResponseMessage;
import com.duyhiep523.user_management.entities.CustomUserDetails;
import com.duyhiep523.user_management.entities.User;
import com.duyhiep523.user_management.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(ResponseMessage.User.USER_NOT_KNOWN + username));
        return new CustomUserDetails(user);
    }
}
