package br.com.develfood.develfood.infra.security;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return  httpSecurity
                .csrf(csrf->csrf.disable())
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/registrar").permitAll()
                        .requestMatchers(HttpMethod.POST,"/plate/filter").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/restaurant").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/plate/create").hasRole("ADMIN")


                        .requestMatchers(HttpMethod.PUT,"/plate/filter/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/restaurant/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/plate/{id}").hasRole("ADMIN")


                        .requestMatchers(HttpMethod.DELETE,"/restaurant/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/plate/filter/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/plate/{id}").hasRole("ADMIN")


                        .requestMatchers(HttpMethod.GET,"/restaurant").permitAll()
                        .requestMatchers(HttpMethod.GET,"/plate/filter").permitAll()
                        .requestMatchers(HttpMethod.GET,"/plate").permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
