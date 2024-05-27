package org.example.config;

import org.example.filters.JwtRequestFilter;
import org.example.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableFeignClients(basePackages = "org.example.proxy")
@EnableWebSecurity
public class ProjectConfig {

//    @Autowired
//    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
        http.httpBasic(Customizer.withDefaults());

        //CORS configurations centralized
        http.cors(c  -> {
            CorsConfigurationSource source = request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(
                        List.of("http://localhost:5173/")
                );
                config.setAllowedMethods(
                        List.of("GET", "POST", "PUT", "DELETE")
                );
                config.setAllowedHeaders(List.of("*"));
                return config;
            };
            c.configurationSource(source);
        });

        //disabled csrf
        http.csrf(
                c -> c.disable()
        );

        http.authorizeHttpRequests(
                auth -> {
                    auth.requestMatchers("/api/customers/login").permitAll();
                            auth.requestMatchers("/api/customers/create").permitAll();
                            auth.requestMatchers("/api/customers/login").permitAll();
                            auth.requestMatchers("/error").permitAll();
                            auth.requestMatchers("/books").permitAll();
                            auth.requestMatchers("/api/books/**").permitAll();
                            auth.anyRequest().authenticated();
                });

                http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//            http.addFilterAfter(
//                    new CsrfTokenLogger(), CsrfFilter.class);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        String customerByUsernameQuery =
                "select username, password, enabled from customer where username = ?";
        String authByUserQuery =
               // "select username, authority from authorities where username = ?";
                "select username, a.name as authority from authorities c_a join authority a on c_a.authority = a.id join customer c on c_a.customer = c.id where username = ?";
        var userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery(customerByUsernameQuery);
        userDetailsManager.setAuthoritiesByUsernameQuery(authByUserQuery);

        return userDetailsManager;
    }
    @Bean
    public JwtRequestFilter jwtRequestFilter(UserDetailsService userDetailsService) {
        return new JwtRequestFilter(userDetailsService, jwtTokenUtil);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
