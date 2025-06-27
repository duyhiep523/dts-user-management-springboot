package com.duyhiep523.user_management.controllers;

import com.duyhiep523.user_management.common.ResponseMessage;
import com.duyhiep523.user_management.dtos.request.LoginRequest;
import com.duyhiep523.user_management.dtos.response.LoginResponse;
import com.duyhiep523.user_management.response.Response;
import com.duyhiep523.user_management.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());

        LoginResponse authResponse = LoginResponse.builder()
                .token(jwt)
                .build();

        Response<Object> response = Response.builder()
                .code(HttpStatus.OK.value())
                .message(ResponseMessage.Authentication.LOGIN_SUCCESS)
                .data(authResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}