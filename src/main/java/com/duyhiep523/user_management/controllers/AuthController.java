package com.duyhiep523.user_management.controllers;

import com.duyhiep523.user_management.common.ResponseMessage;
import com.duyhiep523.user_management.dtos.request.LoginRequest;
import com.duyhiep523.user_management.dtos.request.TokenRefreshRequest;
import com.duyhiep523.user_management.dtos.response.LoginResponse;
import com.duyhiep523.user_management.dtos.response.TokenRefreshResponse;
import com.duyhiep523.user_management.entities.RefreshToken;
import com.duyhiep523.user_management.exeptions.ResourceNotFoundException;
import com.duyhiep523.user_management.response.Response;
import com.duyhiep523.user_management.security.JwtUtil;
import com.duyhiep523.user_management.services.CustomUserDetailsService;
import com.duyhiep523.user_management.services.RefreshTokenService;
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
    private final RefreshTokenService refreshTokenService;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

        LoginResponse authResponse = LoginResponse.builder()
                .token(jwt)
                .refreshToken(refreshToken.getToken())
                .build();

        Response<Object> response = Response.builder()
                .code(HttpStatus.OK.value())
                .message(ResponseMessage.Authentication.LOGIN_SUCCESS)
                .data(authResponse)
                .build();

        return ResponseEntity.ok(response);
    }



    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken)
                .orElseThrow(() -> new ResourceNotFoundException("Refresh token not found"));

        refreshTokenService.verifyExpiration(refreshToken);

        // convert User entity to UserDetails for JWT generation
        UserDetails userDetails = userDetailsService.loadUserByUsername(refreshToken.getUser().getUsername());
        String token = jwtUtil.generateToken(userDetails);

        TokenRefreshResponse responseData = TokenRefreshResponse.builder()
                .accessToken(token)
                .refreshToken(requestRefreshToken)
                .build();

        Response<Object> response = Response.builder()
                .code(HttpStatus.OK.value())
                .message("Token refreshed successfully")
                .data(responseData)
                .build();

        return ResponseEntity.ok(response);
    }


}