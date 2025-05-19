package com.moabom.backend.user.controller;

import com.moabom.backend.user.model.ChangePasswordRequest;
import com.moabom.backend.user.model.UpdateUserRequest;
import com.moabom.backend.user.model.UserDto;
import com.moabom.backend.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping
    public ResponseEntity<Void> updateMyInfo(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateUserRequest req) {
        userService.updateMyInfo(userDetails.getUsername(), req.getNickName(), req.getUserImage());
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

