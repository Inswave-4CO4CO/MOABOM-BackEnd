package com.moabom.backend.wishlist.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moabom.backend.wishlist.model.WishlistContent;
import com.moabom.backend.wishlist.model.WishlistWatch;
import com.moabom.backend.wishlist.model.WishlistResponseDto;
import com.moabom.backend.wishlist.repository.WishlistWatchRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistWatchRepository wishlistWatchRepository;

    @Transactional(readOnly = true)
    public List<WishlistResponseDto> getWishListByUserId(String userId) {
        List<WishlistWatch> watches = wishlistWatchRepository.findByUserIdAndType(userId, "WANT");
        return watches.stream().map(watch -> {
            WishlistContent c = watch.getWishlistContent();
            
            LocalDate releaseDate = c.getReleaseDate().toInstant()
            	    .atZone(ZoneId.systemDefault())
            	    .toLocalDate();
            String castName = c.getWishlistCasts().stream()
                .filter(cast -> "MAIN".equals(cast.getRole()))
                .map(cast -> cast.getPerson().getPersonName())
                .collect(Collectors.joining(", "));
            String crewName = c.getWishlistCrews().stream()
                .filter(crew -> "DIR".equals(crew.getRole()))
                .map(crew -> crew.getPerson().getPersonName())
                .collect(Collectors.joining(", "));
            String ottName = c.getWishlistContentOtts().stream()
                .map(contentOtt -> contentOtt.getWishlistOtt().getOttName())
                .collect(Collectors.joining(", "));

            return new WishlistResponseDto(
                c.getContentId(),
                c.getPoster(),
                c.getMadeIn(),
                c.getTitle(),
                c.getReleaseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                c.getRunningTime(),
                castName,
                crewName,
                ottName
            );
        }).toList();
    }
}