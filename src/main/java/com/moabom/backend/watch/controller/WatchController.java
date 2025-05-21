package com.moabom.backend.watch.controller;

import com.moabom.backend.auth.util.JwtUtil;
import com.moabom.backend.user.exception.MyPageException;
import com.moabom.backend.watch.model.WatchEntity;
import com.moabom.backend.watch.model.WatchId;
import com.moabom.backend.watch.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/watch")
public class WatchController {
    private final WatchService watchService;
    private final JwtUtil jwtUtil;

    @Autowired
    public WatchController(WatchService watchService, JwtUtil jwtUtil) {
        this.watchService = watchService;
        this.jwtUtil = jwtUtil;
    }

    //토큰 리턴하는 거
    private String extractUserIdOrThrow(String token) {
        String realToken = token.startsWith("Bearer ") ? token.substring(7).trim() : token.trim();
        if (realToken.isEmpty()) {
            throw new MyPageException("유효하지 않은 사용자 토큰입니다.");
        }
        return jwtUtil.extractUserId(realToken);
    }

    //시청상태(보고싶다, 보는중, 봤다) 추가
    @PostMapping
    public ResponseEntity<WatchEntity> insert(@RequestBody WatchEntity watchEntity, @RequestHeader(value = "Authorization", defaultValue = "defaultHeaderValue") String userIdAuth) {
        String userId = extractUserIdOrThrow(userIdAuth);

        watchEntity.setUserId(userId);

        WatchEntity savedWatch = watchService.insert(watchEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWatch);
    }

    //시청상태(보고싶다, 보는중, 봤다) 수정
    @PutMapping
    public ResponseEntity<WatchEntity> update(@RequestBody WatchEntity watchEntity, @RequestHeader(value = "Authorization", defaultValue = "defaultHeaderValue") String userIdAuth) {
        String userId = extractUserIdOrThrow(userIdAuth);

        watchEntity.setUserId(userId);

        WatchEntity updatedWatch = watchService.update(watchEntity);
        return ResponseEntity.ok(updatedWatch);
    }

    //시청상태(보고싶다, 보는중, 봤다) 삭제
    @DeleteMapping("/{contentId}")
    public ResponseEntity<String> delete(@PathVariable("contentId") int contentId, @RequestHeader(value = "Authorization", defaultValue = "defaultHeaderValue") String userIdAuth) {
        String userId = extractUserIdOrThrow(userIdAuth);

        WatchId watchId = new WatchId(userId, contentId);
        watchService.delete(watchId);

        return ResponseEntity.ok().body("[시청상태 삭제] 해당 시청상태가 삭제되었습니다 ");
    }
}
