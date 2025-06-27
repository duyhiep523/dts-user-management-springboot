package com.duyhiep523.user_management.services.Iservice;

import com.duyhiep523.user_management.dtos.request.RegisterRequest;
import com.duyhiep523.user_management.dtos.request.UpdateUserRequest;
import com.duyhiep523.user_management.dtos.response.PaginationResponse;
import com.duyhiep523.user_management.dtos.response.RegisterResponse;
import com.duyhiep523.user_management.dtos.response.UserDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {
    RegisterResponse register(RegisterRequest request);


    UserDetailResponse getUserDetail(String userId);

    UserDetailResponse getUserDetailByUsername(String username);

    List<UserDetailResponse> getAllUsers();

    UserDetailResponse updateAvatar(String userId, MultipartFile file);

    void softDeleteUser(String userId);


    PaginationResponse<UserDetailResponse> searchUsers(String keyword, Pageable pageable);

    UserDetailResponse updateUser(String userId, UpdateUserRequest request);

    UserDetailResponse disableUser(String userId);
}
