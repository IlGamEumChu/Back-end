package com.ilgamumchu.demar.controller;

import com.ilgamumchu.demar.domain.User;
import com.ilgamumchu.demar.domain.UserRole;
import com.ilgamumchu.demar.dto.LoginUserDTO;

import com.ilgamumchu.demar.dto.SignUpRequestDTO;
import com.ilgamumchu.demar.security.jwt.JwtTokenProvider;
import com.ilgamumchu.demar.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RequestMapping(value = "/sign")
@RestController
public class SignController {

    private final SignService signService;
    private final JwtTokenProvider jwtTokenProvider;

    @ResponseBody
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        User user = signService.signup(signUpRequestDTO);
        String checkEmail = user.getEmail();
        UserRole role = user.getRole();
        System.out.println(checkEmail);
        String token = jwtTokenProvider.createToken(checkEmail, role);

        return new ResponseEntity<String>(token, HttpStatus.OK);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody LoginUserDTO loginUserDto, HttpServletResponse response) {

        User user = signService.login(loginUserDto);
        String checkEmail = user.getEmail();
        UserRole role = user.getRole();

        String token = jwtTokenProvider.createToken(checkEmail, role);
        response.setHeader("Authorization", token);

        return new ResponseEntity<String>(token, HttpStatus.OK);
    }
}
