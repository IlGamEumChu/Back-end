package com.ilgamumchu.demar.service;

import com.ilgamumchu.demar.repository.UserRepository;
import com.ilgamumchu.demar.security.jwt.UserDetailsImpl;
import com.ilgamumchu.demar.domain.User;
import com.ilgamumchu.demar.utils.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignDetailsServiceImpl {
    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return new UserDetailsImpl(user);
    }
}
