package com.moabom.backend.watch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatchId implements Serializable {
    private String userId;
    private int contentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WatchId)) return false;
        WatchId that = (WatchId) o;
        return contentId == that.contentId && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, contentId);
    }
}
