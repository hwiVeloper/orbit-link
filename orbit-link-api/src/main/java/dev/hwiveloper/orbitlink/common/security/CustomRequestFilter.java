package dev.hwiveloper.orbitlink.common.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomRequestFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final CustomUserDetailsService customUserDetailsService;

    private static final List<String> EXCLUDED_URL_PREFIXES = List.of(
            "/auth", "/policy", "/terms", "/swagger-ui", "/v3/api-docs", "/test", "/api/meta", "/api/auth/instagram"
    );

    public Authentication getAuthentication(String token) {
        Map<String, Object> parseInfo = tokenService.getUserParseInfo(token);

        List<String> rs = (List<String>) parseInfo.get("role");

        Collection<GrantedAuthority> tmp= new ArrayList<>();
        for (String a: rs) {
            tmp.add(new SimpleGrantedAuthority(a));
        }

        UserDetails userDetails = User.builder().username(String.valueOf(parseInfo.get("username"))).authorities(tmp).password("asd").build();
        return new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestAccessToken = request.getHeader("Authorization");
        String requestRefreshToken = request.getHeader(TokenService.REFRESH_TOKEN_NAME);

        if (ObjectUtils.isEmpty(requestAccessToken)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 실패.");
            return;
        }

        String email = null;
        String accessToken = null;
        String refreshToken = null;
        String refreshUsername = null;

        log.info("request access token : {}", requestAccessToken);

        // "Bearer "로 시작하는 access token이 있을 경우
        // "Bearer "는 제거.
        accessToken = requestAccessToken.substring(7);
        logger.info("token in requestfilter: " + accessToken);

        if (requestAccessToken != null && requestAccessToken.startsWith(TokenService.TOKEN_TYPE)) {

            try {
                // 토큰 속 사용자명 조회
                email = tokenService.getEmailFromToken(accessToken);
                if (email != null) {
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                    if (tokenService.validateToken(accessToken, userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                } else {
                    Authentication authentication = getAuthentication(accessToken);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    response.setHeader("email", email);
                }
            } catch (IllegalArgumentException e) {
                logger.warn("Unable to get JWT Token");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "인증 실패.");
                return;
            } catch (ExpiredJwtException e) {
                logger.warn("JWT Token Expired. Check refresh Token");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 실패.");
                return;
            } catch (Exception e) {
                logger.warn("JWT ERROR : ", e);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "인증 실패.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String uri = request.getRequestURI();
        log.info("URI: {}, ServletPath: {}", uri, request.getServletPath());
        return EXCLUDED_URL_PREFIXES.stream().anyMatch(uri::startsWith);
    }
}
