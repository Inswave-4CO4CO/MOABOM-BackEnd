package com.moabom.backend.auth.filter;
import java.io.IOException;

import com.moabom.backend.auth.constants.SecurityConstants;
import com.moabom.backend.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

	    // 더 이상 /uploads/ 경로는 웹서버가 직접 처리 안 하니까
	    // 예외처리 코드 삭제

	    String header = request.getHeader(SecurityConstants.TOKEN_HEADER);

	    if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    String token = header.substring(SecurityConstants.TOKEN_PREFIX.length());

	    String username = jwtUtil.extractUserId(token);

	    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

	        if (jwtUtil.validateToken(token, userDetails)) {
	            UsernamePasswordAuthenticationToken authentication =
	                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	        }
	    }

	    filterChain.doFilter(request, response);
	}

}

