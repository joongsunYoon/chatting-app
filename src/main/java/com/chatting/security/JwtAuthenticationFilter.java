package com.chatting.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        if (uri.startsWith("/api/dev/token")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = request.getHeader("Authorization");
        if (token != null) {
            try {
                jwtTokenProvider.validateAndGetUserId(token);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization 헤더가 없습니다.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
