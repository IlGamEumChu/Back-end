package com.ilgamumchu.demar.service;

import com.ilgamumchu.demar.domain.User;
import com.ilgamumchu.demar.domain.UserRole;
import com.ilgamumchu.demar.dto.SignUpRequestDTO;
import com.ilgamumchu.demar.dto.LoginUserDTO;

import com.ilgamumchu.demar.dto.UserResponseDTO;
import com.ilgamumchu.demar.repository.UserRepository;
import com.ilgamumchu.demar.utils.exception.UserEmailAlreadyExistsException;
import com.ilgamumchu.demar.utils.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = false)
@Service
public class SignService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User signup(SignUpRequestDTO signUpRequestDTO) {
        Date now = new Date();

        String email = signUpRequestDTO.getEmail();

        if(userRepository.existsByEmail(email)){
            throw new UserEmailAlreadyExistsException(email);
        }

        String username = signUpRequestDTO.getName();
        String password = passwordEncoder.encode(signUpRequestDTO.getPassword());
        UserRole role = UserRole.ROLE_USER;
        String spId = signUpRequestDTO.getSpId();
        String spPassword = passwordEncoder.encode(signUpRequestDTO.getSpPassword());

        User user = new User(null,username, email, password, spId, spPassword, role, now);
        userRepository.save(user);
        return user;
    }

    public User login(LoginUserDTO loginUserDto) {
        User user = userRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(loginUserDto.getPassword(), user.getPassword())) {
            throw new UserNotFoundException();
        }
        return user;
    }
}
