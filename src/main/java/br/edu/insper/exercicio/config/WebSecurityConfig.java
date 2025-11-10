package br.edu.insper.exercicio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Apenas administradores podem excluir cursos
                        .requestMatchers(HttpMethod.DELETE, "/cursos/**").hasRole("ADMIN")
                        // Todos os usuários autenticados podem listar e cadastrar cursos
                        .requestMatchers("/cursos").authenticated()
                        .requestMatchers("/cursos/**").authenticated()
                        .anyRequest()
                        .authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<String> roles = jwt.getClaim("https://dev-c5cya7ea1phr4j8p.us.auth0.com/roles");
            if (roles != null) {
                return roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                        .collect(Collectors.toList());
            }
            return Collections.emptyList();
        });
        return converter;
    }
}
