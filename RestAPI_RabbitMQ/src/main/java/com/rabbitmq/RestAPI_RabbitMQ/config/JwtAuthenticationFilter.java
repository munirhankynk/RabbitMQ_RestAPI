package com.rabbitmq.RestAPI_RabbitMQ.config;

import com.rabbitmq.RestAPI_RabbitMQ.model.User;
import com.rabbitmq.RestAPI_RabbitMQ.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    try {
                        String username = jwtUtil.validateAndExtractUsername(token);

                        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                            // Kullanıcıyı doğrula
                            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
                            User user = new User(username, "", authorities);

                            UsernamePasswordAuthenticationToken authentication =
                                    new UsernamePasswordAuthenticationToken(user, null, authorities);

                            SecurityContextHolder.getContext().setAuthentication(authentication);

                            // Token yenileme veya cookie parametrelerini güncelle
                            refreshJwtCookie(token, response);
                        }
                    } catch (Exception e) {
                        logger.warn("JWT token is invalid: " + e.getMessage());
                        clearJwtCookie(response);
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("Invalid JWT token");
                        return;
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }

    private void refreshJwtCookie(String token, HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("JWT", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(7 * 24 * 60 * 60); // 7 gün

        response.addCookie(jwtCookie);
    }

    private void clearJwtCookie(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("JWT", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // Cookie'yi hemen sil

        response.addCookie(jwtCookie);
    }
}
