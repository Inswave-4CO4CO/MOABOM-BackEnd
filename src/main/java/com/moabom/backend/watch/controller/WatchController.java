package com.moabom.backend.watch.controller;

import com.moabom.backend.watch.model.WatchEntity;
import com.moabom.backend.watch.model.WatchId;
import com.moabom.backend.watch.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/watch")
public class WatchController {
    private final WatchService watchService;

    @Autowired
    public WatchController(WatchService watchService) {
        this.watchService = watchService;
    }

    //시청상태(보고싶다, 보는중, 봤다) 추가
    @PostMapping
    public ResponseEntity<WatchEntity> insert(@RequestBody WatchEntity watchEntity) {
        WatchEntity savedWatch = watchService.insert(watchEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWatch);
    }

    //시청상태(보고싶다, 보는중, 봤다) 수정
    @PutMapping
    public ResponseEntity<WatchEntity> update(@RequestBody WatchEntity watchEntity) {
        WatchEntity updatedWatch = watchService.update(watchEntity);
        return ResponseEntity.ok(updatedWatch);
    }

    //시청상태(보고싶다, 보는중, 봤다) 삭제
    @DeleteMapping("/{contentId}")
    public ResponseEntity<String> delete(@RequestParam("userId") String userId, @PathVariable("contentId") int contentId) {
        WatchId watchId = new WatchId(userId, contentId);
        watchService.delete(watchId);
        return ResponseEntity.ok().body("[시청상태 삭제] 해당 시청상태가 삭제되었습니다 ");
    }
}
