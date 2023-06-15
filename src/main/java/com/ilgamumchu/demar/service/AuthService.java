package com.ilgamumchu.demar.service;

import com.ilgamumchu.demar.common.ErrorMessage;
import com.ilgamumchu.demar.domain.User;
import com.ilgamumchu.demar.dto.SignUpRequestDTO;
import com.ilgamumchu.demar.dto.LoginUserDTO;

import com.ilgamumchu.demar.dto.AuthResponseDTO;
import com.ilgamumchu.demar.repository.UserRepository;
import com.ilgamumchu.demar.security.jwt.AdminAuthentication;
import com.ilgamumchu.demar.security.jwt.JwtTokenProvider;
import com.ilgamumchu.demar.utils.exception.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional(readOnly = false)
@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    //private final MusicRepository musicRepository;
    //private final PlayListTrackRepository playListTrackRepository;
    private final JwtTokenProvider jwtTokenprovider;

    public AuthResponseDTO signup(SignUpRequestDTO signUpRequestDTO){
        val email = signUpRequestDTO.email();
        val username = signUpRequestDTO.name();

        userRepository.existsByEmail(email)
                .orElseThrow(() -> new AuthException(ErrorMessage.DUPLICATED_EMAIL.getName()));

        val password = passwordEncoder.encode(signUpRequestDTO.password());

        val user = userRepository.save(User.builder()
                .email(signUpRequestDTO.email())
                .name(username)
                .password(password)
                .build());

        val authentication = new AdminAuthentication(user.getId(), null, null);

        return AuthResponseDTO.of(user.getName(), jwtTokenprovider.generateAccessToken(authentication));
    }

    public AuthResponseDTO login(LoginUserDTO loginUserDto) {
        val user = userRepository.findByEmail(loginUserDto.email())
                .orElseThrow(() -> new AuthException(ErrorMessage.INVALID_EMAIL.getName()));

        if (!passwordEncoder.matches(loginUserDto.password(), user.getPassword())) {
            throw new AuthException(ErrorMessage.INVALID_PASSWORD.getName());
        }

        val authentication = new AdminAuthentication(user.getId(), null, null);

        return AuthResponseDTO.of(user.getName(), jwtTokenprovider.generateAccessToken(authentication));
    }
}
