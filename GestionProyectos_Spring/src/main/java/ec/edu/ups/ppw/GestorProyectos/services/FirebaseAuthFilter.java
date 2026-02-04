package ec.edu.ups.ppw.GestorProyectos.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FirebaseAuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(FirebaseAuthFilter.class);

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        return path.equals("/")
                || path.startsWith("/error")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.equals("/swagger-ui.html")
                || path.startsWith("/actuator")
                || path.endsWith("/health");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || authHeader.isBlank()) {
            log.warn("401 - Authorization NO llegó. method={} uri={}", request.getMethod(), request.getRequestURI());
            unauthorized(response, "Token JWT requerido");
            return;
        }

        if (!authHeader.regionMatches(true, 0, "Bearer ", 0, 7)) {
            log.warn("401 - Authorization no es Bearer. method={} uri={} header={}",
                    request.getMethod(), request.getRequestURI(), authHeader);
            unauthorized(response, "Token JWT requerido");
            return;
        }

        String token = authHeader.substring(7).trim();
        if (token.isEmpty()) {
            log.warn("401 - Bearer vacío. method={} uri={}", request.getMethod(), request.getRequestURI());
            unauthorized(response, "Token JWT requerido");
            return;
        }

        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(decodedToken.getUid(), null, null);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            log.warn("401 - Token inválido. method={} uri={} error={}",
                    request.getMethod(), request.getRequestURI(), e.getMessage());
            unauthorized(response, "Token invalido: " + e.getMessage());
        }
    }

    private void unauthorized(HttpServletResponse response, String msg) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.getWriter().write(msg);
    }
}
