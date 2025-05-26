package com.moabom.backend.watch.controller;

import com.moabom.backend.auth.util.JwtUtil;
import com.moabom.backend.user.exception.MyPageException;
import com.moabom.backend.watch.model.WatchDTO;
import com.moabom.backend.watch.model.WatchEntity;
import com.moabom.backend.watch.model.WatchId;
import com.moabom.backend.watch.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/watch")
public class WatchController {
    private final WatchService watchService;

    @Autowired
    public WatchController(WatchService watchService) {
        this.watchService = watchService;
    }

    //시청상태(보고싶다, 보는중, 봤다) 추가
    @PostMapping
    public ResponseEntity<WatchEntity> insert(@RequestBody WatchDTO watchDTO, @AuthenticationPrincipal UserDetails userDetails) {
        //시청상태 엔티티
        WatchEntity watchEntity = new WatchEntity();
        watchEntity.setUserId(userDetails.getUsername());
        watchEntity.setContentId(watchDTO.getContentId());
        watchEntity.setType(watchDTO.getType());
        watchDTO.setUserId(userDetails.getUsername());

        watchService.insert(watchDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(watchEntity);
    }

    //시청상태(보고싶다, 보는중, 봤다) 수정
    @PutMapping
    public ResponseEntity<WatchEntity> update(@RequestBody WatchDTO watchDTO, @AuthenticationPrincipal UserDetails userDetails) {
        watchDTO.setUserId(userDetails.getUsername());

        WatchEntity updatedWatch = watchService.update(watchDTO);
        return ResponseEntity.ok(updatedWatch);
    }

    //시청상태(보고싶다, 보는중, 봤다) 삭제
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody WatchDTO watchDTO, @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(userDetails.getUsername());
        watchDTO.setUserId(userDetails.getUsername());
        watchService.delete(watchDTO);

        return ResponseEntity.ok().body("[시청상태 삭제] 해당 시청상태가 삭제되었습니다 ");
    }
}
