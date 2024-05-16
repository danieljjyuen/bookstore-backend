package org.example.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.vault.core.util.PropertyTransformers;

import javax.sql.DataSource;

@Configuration
@EnableFeignClients(basePackages = "org.example.proxy")
public class ProjectConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());

        http.authorizeHttpRequests(
                auth -> {
                    auth.requestMatchers("/api/customers/login").permitAll();
                            auth.requestMatchers("/api/customers/create").permitAll();
                            auth.requestMatchers("/error").permitAll();
                            auth.anyRequest().authenticated();
                });
        http.csrf(
                c -> c.disable()
        );
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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
