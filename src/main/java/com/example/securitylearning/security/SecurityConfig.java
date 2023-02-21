package com.example.securitylearning.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/students/4").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/management/api/**").hasAnyAuthority(COURSE_WRITE.getPermission())
//                .requestMatchers(HttpMethod.PUT, "/management/api/**").hasAnyAuthority(COURSE_WRITE.getPermission())
//                .requestMatchers(HttpMethod.POST, "/management/api/**").hasAnyAuthority(COURSE_WRITE.getPermission())
//                .requestMatchers("/management/api/**").hasAnyRole(ADMIN.name(), ADMIN_TRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/courses", true)
                .and()
                .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                    .key("secured")
                .and()
                .logout()
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("remember-me", "JSESSIONID")
                .and()
                    .authenticationProvider(daoAuthenticationProvider());

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                .username("alexander")
//                .password(passwordEncoder().encode("password"))
////                .roles(UserRole.STUDENT.name())
//                .authorities(STUDENT.getGrantedAuthority())
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
////                .roles(ADMIN.name())
//                .authorities(ADMIN.getGrantedAuthority())
//                .build();
//
//        UserDetails adminTrainee = User.builder()
//                .username("adminTrainee")
//                .password(passwordEncoder().encode("adminTrainee"))
////                .roles(ADMIN_TRAINEE.name())
//                .authorities(ADMIN_TRAINEE.getGrantedAuthority())
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin, adminTrainee);
//    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
}
