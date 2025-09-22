package dev.hwiveloper.orbitlink.common.config;

import dev.hwiveloper.orbitlink.common.filter.RequestCachingFilter;
import dev.hwiveloper.orbitlink.common.security.CustomAccessDeniedHandler;
import dev.hwiveloper.orbitlink.common.security.CustomAuthenticationEntryPoint;
import dev.hwiveloper.orbitlink.common.security.CustomRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomAuthenticationEntryPoint entryPoint;

    private final CustomAccessDeniedHandler accessDeniedHandler;

    private final CustomRequestFilter customRequestFilter;

    private final RequestCachingFilter requestCachingFilter;

    private static final String[] PERMIT_URL_ARRAY = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.index.html",
            "/webjars/**",
            "/swagger-resources/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector hmi) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headerConfig -> headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(ahr -> ahr
                        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/auth/**")).anonymous()
                        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/policy/**")).anonymous()
                        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/terms/**")).anonymous()
//                        .requestMatchers(new MvcRequestMatcher(hmi, "/swagger-ui/**")).permitAll()
//                        .requestMatchers(new MvcRequestMatcher(hmi, "/v3/api-docs")).permitAll()
//                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers(PERMIT_URL_ARRAY).permitAll()
                        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/test/**")).anonymous()
                        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/api/meta/**")).anonymous()
                        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/api/auth/instagram")).anonymous()
                        .anyRequest().authenticated())
                .exceptionHandling(c -> c
                        .authenticationEntryPoint(entryPoint)
//                        .accessDeniedHandler(accessDeniedHandler)

                )
                .addFilterBefore(requestCachingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 리소스 spring security 대상에서 제외
        return (web) -> web
                .ignoring()
                .requestMatchers("/auth/**", "/terms/**", "/test/**", "/api/meta/**", "/api/auth/instagram/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers(PERMIT_URL_ARRAY);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
