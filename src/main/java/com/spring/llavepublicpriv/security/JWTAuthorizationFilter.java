package com.spring.llavepublicpriv.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.spring.llavepublicpriv.services.IJWTUtilityService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final IJWTUtilityService jwtUtilityService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        boolean shouldSkip = path.startsWith("/auth/");
        return shouldSkip;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        log.debug("🔐 Procesando JWT para: {} {}", request.getMethod(), request.getRequestURI());

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            log.warn("⚠️  No se encontró token JWT válido en el header Authorization");
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        log.debug("📝 Token JWT encontrado (primeros 20 chars): {}...", token.substring(0, Math.min(20, token.length())));

        try {
            JWTClaimsSet claims = jwtUtilityService.parseJWT(token);
            String userId = claims.getSubject();
            List<String> roles = (List<String>) claims.getClaim("roles");

            log.info("✅ JWT válido para usuario: {} con roles: {}", userId, roles);

            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userId, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.debug("🔓 Usuario autenticado en SecurityContext");

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | ParseException | JOSEException e) {
            log.error("❌ Error validando JWT: {} - {}", e.getClass().getSimpleName(), e.getMessage());
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}