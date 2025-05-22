package com.moabom.backend.user.controller;

import com.moabom.backend.user.model.ChangePasswordRequest;
import com.moabom.backend.user.model.UpdateUserRequest;
import com.moabom.backend.user.model.UserDto;
import com.moabom.backend.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 내 정보 조회
    @GetMapping
    public ResponseEntity<UserDto> getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getMyInfo(userDetails.getUsername()));
    }

    // 내 정보 변경
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateMyInfo(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestPart("request") UpdateUserRequest request,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage
    ) {
        userService.updateMyInfo(userDetails.getUsername(), request.getNickName(), profileImage);
        return ResponseEntity.ok().build();
    }


    // 내 비밀번호 변경
    @PutMapping("/password")
    public ResponseEntity<Void> changeMyPassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ChangePasswordRequest req) {
        userService.changeMyPassword(userDetails.getUsername(), req.getNewPassword());
        return ResponseEntity.ok().build();
    }
}

