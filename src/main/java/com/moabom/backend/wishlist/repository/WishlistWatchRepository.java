package com.moabom.backend.wishlist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moabom.backend.wishlist.model.WishlistWatch;
import com.moabom.backend.wishlist.model.WishlistWatchId;

@Repository
public interface WishlistWatchRepository extends JpaRepository<WishlistWatch, WishlistWatchId> {
    List<WishlistWatch> findByUserIdAndType(String userId, String type);
}
