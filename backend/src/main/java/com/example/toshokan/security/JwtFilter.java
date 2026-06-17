package com.example.toshokan.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		
	    String path = request.getServletPath();
	    System.out.println("SHOULD NOT FILTER PATH: " + path);
	    return path.startsWith("/api/login") || path.startsWith("/api/signup") || path.equals("/login")
	            || path.equals("/error");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 🔥 デバッグログ（ここが超重要）
		System.out.println("==== JWT FILTER HIT ====");
		System.out.println("REQUEST URI: " + request.getRequestURI());
		System.out.println("AUTH HEADER: " + request.getHeader("Authorization"));

		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer ")) {
			try {
				String token = header.substring(7);
				String username = JwtUtil.extractUsername(token);

				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
						List.of(new SimpleGrantedAuthority("ROLE_USER")));

				SecurityContextHolder.getContext().setAuthentication(auth);

				System.out.println("AUTH SUCCESS SET");
				System.out.println("AUTH AFTER SET = " + SecurityContextHolder.getContext().getAuthentication());

			} catch (Exception e) {
				System.out.println("JWT INVALID → BLOCK OR SKIP AUTH");
				e.printStackTrace();

				SecurityContextHolder.clearContext();
			}
		} else {
			System.out.println("NO BEARER TOKEN FOUND");
			SecurityContextHolder.clearContext();
		}
		  filterChain.doFilter(request, response);
	}
	
}