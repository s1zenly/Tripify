package com.tripify.auth.service.jwt;

import java.io.IOException;
import java.util.List;

import com.tripify.auth.generated.model.ErrorCode;
import com.tripify.auth.service.Service.CookieService;
import com.tripify.auth.service.client.Messages;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CookieService cookieService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String accessToken = cookieService.getCookieValue(request, cookieService.accessTokenCookieName())
                .orElse(null);

        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String userId = jwtService.extractSubject(accessToken);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            List.of()
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            authException(response);
        }
    }

    private void authException(HttpServletResponse response) throws IOException {
        SecurityContextHolder.clearContext();

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("""
                    {"code":"%s","message":"%s"}
                    """.formatted(ErrorCode.ACCESS_TOKEN_INVALID, Messages.ACCESS_TOKEN_INVALID_MESSAGE));
    }
}
