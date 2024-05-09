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
                        .requestMatchers(HttpMethod.POST,"/type/filter").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/restaurant").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/plate/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/seding-email").permitAll()
                        .requestMatchers(HttpMethod.POST,"/client/create").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/address/create").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/logout").permitAll()
                        .requestMatchers(HttpMethod.POST,"/pedidos/create").permitAll()
                        .requestMatchers(HttpMethod.POST,"/avaliacao/{id}/avaliar").permitAll()
                        .requestMatchers(HttpMethod.POST,"/favoritos/pratos").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/redefinir-senha").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/esqueci-senha").permitAll()
                        .requestMatchers(HttpMethod.POST,"/promotions/restaurants/{restaurantId}").permitAll()


                        .requestMatchers(HttpMethod.PUT,"/type/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/restaurant/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/restaurant/plate/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/client/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT,"/address/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/pedidos/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/avaliacao/{avaliacaoId}").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/pedidos/{pedidoId}/status").permitAll()

                        .requestMatchers(HttpMethod.DELETE,"/restaurant/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/type/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/restaurant/plate/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/address/customer/*/address/*").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/pedidos/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/avaliacao/{avaliacaoId}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/favoritos/{clienteId}/pratos/{pratoFavoritoId}").permitAll()

                        .requestMatchers(HttpMethod.GET,"/restaurant").permitAll()
                        .requestMatchers(HttpMethod.GET,"/type").permitAll()
                        .requestMatchers(HttpMethod.GET,"/restaurant/plate").permitAll()
                        .requestMatchers(HttpMethod.GET,"/restaurant/plate/list").permitAll()
                        .requestMatchers(HttpMethod.GET,"/client/all").permitAll()
                        .requestMatchers(HttpMethod.GET,"/address/list").permitAll()
                        .requestMatchers(HttpMethod.GET,"/pedidos/list").permitAll()
                        .requestMatchers(HttpMethod.GET,"/avaliacao/restaurant/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/favoritos/{clienteId}/list").permitAll()

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
