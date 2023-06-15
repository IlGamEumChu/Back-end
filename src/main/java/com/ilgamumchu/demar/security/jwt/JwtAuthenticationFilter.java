package com.ilgamumchu.demar.security.jwt;

import com.ilgamumchu.demar.common.ErrorMessage;
import com.ilgamumchu.demar.utils.exception.TokenException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        val uri = request.getRequestURI();

        if ((uri.startsWith("/api/v2")) && !uri.contains("auth")) {
            val token = jwtTokenProvider.resolveToken(request);

            val isTokenAvailable = checkJwtAvailable(token);

            if (Objects.isNull(token)) {
                throw new TokenException(ErrorMessage.INVALID_AUTH_REQUEST.getName());
            }

            if (isTokenAvailable) {
                val auth = jwtTokenProvider.getAuthentication(token);
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);
    }

    private boolean checkJwtAvailable (String token) {
        return token != null && jwtTokenProvider.validateTokenExpiration(token);
    }
}