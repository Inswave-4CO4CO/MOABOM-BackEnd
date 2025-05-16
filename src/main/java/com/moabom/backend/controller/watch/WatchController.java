package com.moabom.backend.controller.watch;

import com.moabom.backend.model.watch.WatchEntity;
import com.moabom.backend.model.watch.WatchId;
import com.moabom.backend.service.watch.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WatchController {
    @Autowired
    private WatchService watchService;

    @PostMapping("/content")
    public void insert(@RequestBody WatchEntity watchEntity) {
        watchService.insert(watchEntity);
    }

    @PutMapping("/content")
    public void update(@RequestBody WatchEntity watchEntity) {
        watchService.update(watchEntity);
    }

    @DeleteMapping("/content/{contentId}")
    public void delete(@PathVariable("contentId") int contentId, @RequestParam("userId") String userId) {
        WatchId watchId = new WatchId(userId, contentId);
        watchService.delete(watchId);
    }
}
