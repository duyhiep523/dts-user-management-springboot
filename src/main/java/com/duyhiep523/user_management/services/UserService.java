package com.duyhiep523.user_management.services;

import com.duyhiep523.user_management.common.ResponseMessage;
import com.duyhiep523.user_management.common.enums.Gender;
import com.duyhiep523.user_management.dtos.request.RegisterRequest;
import com.duyhiep523.user_management.dtos.request.UpdateUserRequest;
import com.duyhiep523.user_management.dtos.response.PaginationResponse;
import com.duyhiep523.user_management.dtos.response.RegisterResponse;
import com.duyhiep523.user_management.dtos.response.UserDetailResponse;
import com.duyhiep523.user_management.entities.User;
import com.duyhiep523.user_management.exeptions.BadRequestException;
import com.duyhiep523.user_management.exeptions.IllegalArgumentCustomException;
import com.duyhiep523.user_management.exeptions.ResourceNotFoundException;
import com.duyhiep523.user_management.repositories.UserRepository;
import com.duyhiep523.user_management.services.Iservice.IUserService;
import com.duyhiep523.user_management.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentCustomException(ResponseMessage.User.PASSWORD_NOT_MATCH);
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentCustomException(ResponseMessage.User.USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentCustomException(ResponseMessage.User.EMAIL_ALREADY_EXISTS);
        }
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentCustomException(ResponseMessage.User.PHONE_ALREADY_EXISTS);
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();

        User savedUser = userRepository.save(user);
        return RegisterResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .gender(user.getGender())
                .profilePictureUrl(savedUser.getProfilePictureUrl())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public UserDetailResponse getUserDetail(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.User.USER_NOT_FOUND));

        return buildUserDetail(user);
    }

    @Override
    public UserDetailResponse getUserDetailByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.User.USER_NOT_FOUND));

        return buildUserDetail(user);
    }


    @Override
    public List<UserDetailResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(this::buildUserDetail)
                .toList();
    }


    @Override
    public UserDetailResponse updateAvatar(String userId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException(ResponseMessage.User.AVATAR_EMPTY);
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.User.USER_NOT_FOUND));

        String imageUrl = cloudinaryService.uploadImage(file);

        user.setProfilePictureUrl(imageUrl);
        userRepository.save(user);

        return buildUserDetail(user);
    }

    @Override
    public void softDeleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.User.USER_NOT_FOUND));
        if (Boolean.TRUE.equals(user.getIsDeleted())) {
            throw new BadRequestException(ResponseMessage.User.USER_ALREADY_DELETED);
        }
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    @Override
    public PaginationResponse<UserDetailResponse> searchUsers(String keyword, Pageable pageable) {
        Page<User> users = userRepository.findAll(UserSpecification.searchByKeyword(keyword), pageable);
        List<UserDetailResponse> userResponses = users.map(this::buildUserDetail).getContent();

        return PaginationResponse.<UserDetailResponse>builder()
                .content(userResponses)
                .page(users.getNumber())
                .size(users.getSize())
                .totalElements(users.getTotalElements())
                .totalPages(users.getTotalPages())
                .last(users.isLast())
                .build();
    }


    @Override
    public UserDetailResponse updateUser(String userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.User.USER_NOT_FOUND));

        if (!user.getEmail().equals(request.getEmail()) &&
                userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException(ResponseMessage.User.EMAIL_USED);
        }
        if (!user.getPhone().equals(request.getPhone()) &&
                userRepository.existsByPhone(request.getPhone())) {
            throw new BadRequestException(ResponseMessage.User.PHONE_USED);
        }
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setDob(request.getDob());
        user.setGender(Gender.valueOf(request.getGender().toUpperCase()));

        userRepository.save(user);

        return buildUserDetail(user);
    }

    @Override
    public UserDetailResponse disableUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.User.USER_NOT_FOUND));

        if (!user.getActive()) {
            throw new BadRequestException(ResponseMessage.User.USER_ALREADY_DISABLED);
        }

        user.setActive(false);
        userRepository.save(user);

        return buildUserDetail(user);
    }


    private UserDetailResponse buildUserDetail(User user) {
        return UserDetailResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .gender(user.getGender())
                .profilePictureUrl(user.getProfilePictureUrl())
                .role(user.getRole().name())
                .active(user.getActive())
                .dob(user.getDob())
                .isDeleted(user.getIsDeleted())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}