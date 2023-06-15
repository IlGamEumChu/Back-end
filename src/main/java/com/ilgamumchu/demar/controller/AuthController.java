package com.ilgamumchu.demar.controller;

import com.ilgamumchu.demar.common.ApiResponse;
import static com.ilgamumchu.demar.common.ResponseMessage.*;

import com.ilgamumchu.demar.dto.LoginUserDTO;

import com.ilgamumchu.demar.dto.SignUpRequestDTO;
import com.ilgamumchu.demar.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v2/auth")
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "회원 가입")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(@RequestBody SignUpRequestDTO request) {
        val response = authService.signup(request);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_SIGN_UP.getMessage(), response));
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginUserDTO request) {
        val response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_LOGIN.getMessage(), response));
    }
}
