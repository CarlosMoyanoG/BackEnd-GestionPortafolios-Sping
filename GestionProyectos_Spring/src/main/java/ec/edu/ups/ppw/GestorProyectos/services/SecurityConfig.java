package ec.edu.ups.ppw.GestorProyectos.services; // o .config (pero SOLO en 1 lugar)

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final FirebaseAuthFilter firebaseAuthFilter;

    public SecurityConfig(FirebaseAuthFilter firebaseAuthFilter) {
        this.firebaseAuthFilter = firebaseAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",                      
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html"
                ).permitAll()

                .requestMatchers("/api/**").authenticated()

                .anyRequest().authenticated()
            )
            .addFilterBefore(firebaseAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
