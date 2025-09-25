package br.com.gustavo.gym.organizer.security;

import br.com.gustavo.gym.organizer.service.TokenService;
import br.com.gustavo.gym.organizer.service.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UsersService usersService) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(usersService).passwordEncoder(passwordEncoder());
        return authBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UsersService usersService, TokenService tokenService) throws Exception {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(tokenService, usersService);

        http
                // Desabilita CSRF pois estamos usando JWT
                .csrf(csrf -> csrf.disable())

                // Configura endpoints públicos e protegidos
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/users/login",
                                "/users/register",
                                "/error",
                                "/oauth2/**",
                                "/login/oauth2/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                // Configura OAuth2 Login (Google)
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // Página de login caso queira front-end
                        .defaultSuccessUrl("/users/google") // Redireciona para endpoint do Google
                )

                // Configura logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                );

        // Adiciona filtro JWT antes do filtro padrão de autenticação
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
