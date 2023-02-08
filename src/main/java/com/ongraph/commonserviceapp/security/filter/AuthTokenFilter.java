package com.ongraph.commonserviceapp.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ongraph.commonserviceapp.cache.UserCacheRepository;
import com.ongraph.commonserviceapp.context.UserDetailsContextHolder;
import com.ongraph.commonserviceapp.model.UserDetailsImpl;
import com.ongraph.commonserviceapp.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	UserCacheRepository userCacheRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.debug("AuthTokenFilter.doFilterInternal()");
		var token = parseJwt(request);
		if (token != null && jwtUtil.validateJwtToken(token)) {
			var username = jwtUtil.getUserNameFromJwtToken(token);
			var userDetails=userCacheRepository.findByUserName(username);
			var userDetailsImpl =UserDetailsImpl.build(userDetails);

			var authentication = new UsernamePasswordAuthenticationToken(userDetailsImpl, null,
					userDetailsImpl.getAuthorities());

			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			UserDetailsContextHolder.set(userDetails);
			SecurityContextHolder.getContext().setAuthentication(authentication);

		}

		filterChain.doFilter(request, response);

	}

	private String parseJwt(HttpServletRequest request) {
		var headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}
}