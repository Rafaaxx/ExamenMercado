package org.example.ratelimit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.service.RateLimitingService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class RateLimitFilter extends OncePerRequestFilter {
    private final RateLimitingService rateLimitingService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String ip=request.getRemoteAddr();
        if(!rateLimitingService.permitido(ip)) {
            response.setStatus(429);
            response.getWriter().write("Demasiadas peticiones. Limite de requests excedido.");
            return;
        }
        filterChain.doFilter(request,response);
    }
}
