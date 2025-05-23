package com.moabom.backend.wishlist.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moabom.backend.wishlist.model.WishlistResponseDto;
import com.moabom.backend.wishlist.service.WishlistService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/wishlist")
public class WishlistController {
	private final WishlistService wishlistService;

	@GetMapping
	public ResponseEntity<List<WishlistResponseDto>> getWishlist(@AuthenticationPrincipal UserDetails userDetails) {
		String userId = userDetails.getUsername();
		List<WishlistResponseDto> list = wishlistService.getWishListByUserId(userId);
		return ResponseEntity.ok(list);
	}
}
