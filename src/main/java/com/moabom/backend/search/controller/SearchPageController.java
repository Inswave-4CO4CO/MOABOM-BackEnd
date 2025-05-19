package com.moabom.backend.domain.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moabom.backend.domain.search.model.ContentDto;
import com.moabom.backend.domain.search.model.SearchRequestDto;
import com.moabom.backend.domain.search.model.SearchResultDto;
import com.moabom.backend.domain.search.service.SearchService;

@RestController
@RequestMapping("/search")
public class SearchPageController {

	@Autowired
	private SearchService searchService;

	@GetMapping
	public Object search(@ModelAttribute SearchRequestDto req) {
		Pageable pg = PageRequest.of(req.getPage(), req.getSize());
		switch (req.getType()) {
		case "cast":
			return searchService.searchCastByName(req.getKeyword(), pg);
		case "crew":
			return searchService.searchCrewByName(req.getKeyword(), pg);

		default: // content
			Sort sortObj = switch (req.getSort()) {
			case "latest" -> Sort.by(Sort.Direction.DESC, "releaseDate", "contentId");
			case "oldest" -> Sort.by(Sort.Direction.ASC, "releaseDate", "contentId");
			default -> Sort.by(Sort.Direction.DESC, "rating", "contentId");
			};
			Pageable pageable = PageRequest.of(req.getPage(), req.getSize(), sortObj);

			// 2) 실제 ContentDto 리스트 조회
			Page<ContentDto> page = searchService.searchWithFilters(req.getKeyword(), req.getGenres(), req.getOtts(),
					req.getCategories(), pg);

			// 3) 초기 요청 여부 판단 (page==0 && no cursor)
			boolean isFirst = req.getPage() == 0 && req.getAfterRating() == null && req.getAfterDate() == null
					&& req.getAfterId() == null;

			// 4) 응답 DTO 조립
			SearchResultDto resp = new SearchResultDto();
			resp.setContent(page.getContent());
			resp.setHasNext(page.hasNext());

			if (isFirst) {
				resp.setAllGenres(searchService.getAllGenres());
				resp.setAllCategories(searchService.getAllCategories());
			}
			return resp;
		}
	}
}
