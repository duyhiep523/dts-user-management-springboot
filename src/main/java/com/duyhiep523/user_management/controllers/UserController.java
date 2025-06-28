package com.duyhiep523.user_management.controllers;

import com.duyhiep523.user_management.common.ResponseMessage;
import com.duyhiep523.user_management.dtos.request.RegisterRequest;
import com.duyhiep523.user_management.dtos.request.UpdateUserRequest;
import com.duyhiep523.user_management.dtos.response.PaginationResponse;
import com.duyhiep523.user_management.dtos.response.RegisterResponse;
import com.duyhiep523.user_management.dtos.response.UserDetailResponse;
import com.duyhiep523.user_management.exeptions.BadRequestException;
import com.duyhiep523.user_management.response.Response;
import com.duyhiep523.user_management.services.Iservice.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "${apiPrefix}/users")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest userDTO) {

        RegisterResponse registeredUser = iUserService.register(userDTO);
        Response<Object> response = Response.builder()
                .code(HttpStatus.CREATED.value())
                .message(ResponseMessage.User.REGISTER_SUCCESS)
                .data(registeredUser)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        UserDetailResponse user = iUserService.getUserDetail(userId);
        Response<Object> response = Response.builder()
                .code(HttpStatus.OK.value())
                .message(ResponseMessage.User.GET_BY_ID_SUCCESS)
                .data(user)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        UserDetailResponse user = iUserService.getUserDetailByUsername(username);
        Response<Object> response = Response.builder()
                .code(HttpStatus.OK.value())
                .message(ResponseMessage.User.GET_BY_USERNAME_SUCCESS)
                .data(user)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<UserDetailResponse> users = iUserService.getAllUsers();
        Response<Object> response = Response.builder()
                .code(HttpStatus.OK.value())
                .message(ResponseMessage.User.GET_ALL_SUCCESS)
                .data(users)
                .build();
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{userId}/avatar")
    public ResponseEntity<?> updateAvatar(@PathVariable String userId,
                                          @RequestParam("file") MultipartFile[] files) {
        if (files == null || files.length != 1) {
            throw new BadRequestException(ResponseMessage.User.REQUIRE_SINGLE_FILE);
        }
        UserDetailResponse user = iUserService.updateAvatar(userId, files[0]);
        Response<Object> response = Response.builder()
                .code(HttpStatus.OK.value())
                .message(ResponseMessage.User.UPDATE_AVATAR_SUCCESS)
                .data(user)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        iUserService.softDeleteUser(userId);
        Response<Object> response = Response.builder()
                .code(HttpStatus.OK.value())
                .message(ResponseMessage.User.DELETE_SOFT_SUCCESS)
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(
            @RequestParam("keyword") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "username") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        PaginationResponse<UserDetailResponse> result = iUserService.searchUsers(keyword, pageable);

        Response<Object> response = Response.builder()
                .code(HttpStatus.OK.value())
                .message(ResponseMessage.User.SEARCH_SUCCESS)
                .data(result)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId,
                                        @Valid @RequestBody UpdateUserRequest request) {
        UserDetailResponse updatedUser = iUserService.updateUser(userId, request);
        Response<Object> response = Response.builder()
                .code(HttpStatus.OK.value())
                .message(ResponseMessage.User.UPDATE_INFO_SUCCESS)
                .data(updatedUser)
                .build();
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{userId}/disable")
    public ResponseEntity<?> disableUser(@PathVariable String userId) {
        UserDetailResponse user = iUserService.disableUser(userId);
        Response<Object> response = Response.builder()
                .code(HttpStatus.OK.value())
                .message(ResponseMessage.User.DISABLE_SUCCESS)
                .data(user)
                .build();
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{userId}/role/admin")
    public ResponseEntity<?> updateRoleToAdmin(@PathVariable String userId) {
        UserDetailResponse userResponse = iUserService.updateRoleToAdmin(userId);

        Response<Object> response = Response.builder()
                .code(HttpStatus.OK.value())
                .message(ResponseMessage.User.UPDATE_ROLE_TO_ADMIN_SUCCESS)
                .data(userResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        UserDetailResponse userResponse = iUserService.getCurrentUser();

        Response<Object> response = Response.builder()
                .code(HttpStatus.OK.value())
                .message(ResponseMessage.User.Get_ME_SUCCESS)
                .data(userResponse)
                .build();

        return ResponseEntity.ok(response);
    }

}