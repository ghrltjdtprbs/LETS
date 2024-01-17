package com.yanolja_final.global.config.security;

import com.yanolja_final.global.config.security.jwt.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] ALLOWED_PATHS
        = {
        "/v1/docs/**", "/v1/users/email/**", "/h2-console/**", "/health", "/v1/notices",
        "/v1/notices/**", "/v1/faq", "/v1/faq/**", "/v1/reviews/packages/**",
        "/v1/packages/**", "/v1/advertisements", "/v1/advertisements/**", "/v1/themes/**"
    };

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // JWT 인증을 사용하므로 CSRF 보안 해제 및 세션 사용안함 설정
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
        ;

        // h2-console을 위한 FrameOption 허용
        http.headers(headers ->
            headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
        );

        http.exceptionHandling(exceptionHandling -> exceptionHandling
            .accessDeniedHandler(
                (request, response, accessDeniedException)
                    -> response.sendError(HttpServletResponse.SC_FORBIDDEN)
            )
            .authenticationEntryPoint(
                (request, response, accessDeniedException)
                    -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
            )
        );

        // REST API의 URI에 대한 인가 적용/미적용 설정
        http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
            .requestMatchers(
                Arrays.stream(ALLOWED_PATHS)
                    .map(AntPathRequestMatcher::new)
                    .toArray(RequestMatcher[]::new)
            ).permitAll()
            .requestMatchers(
                new AntPathRequestMatcher("/v1/user", HttpMethod.POST.name())
            ).permitAll()
            .anyRequest().authenticated()
        );

        http.addFilterBefore(
            jwtFilter,
            UsernamePasswordAuthenticationFilter.class
        );

        http.cors(httpSecurityCorsConfigurer ->
            httpSecurityCorsConfigurer
                .configurationSource(corsConfigurationSource())
        );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOriginPattern("http://localhost:3000");
        corsConfiguration.addAllowedOriginPattern("https://winnerone.site");
        corsConfiguration.addAllowedOriginPattern("https://www.winnerone.site");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
