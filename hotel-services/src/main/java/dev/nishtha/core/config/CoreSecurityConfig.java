package dev.nishtha.core.config;

import dev.nishtha.core.contracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
@Order(2)
public class CoreSecurityConfig {

    private final UserService customUserDetails;

    @Bean
    public SecurityFilterChain coreSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(httpRequests ->
                        httpRequests.requestMatchers("/secured/web/users/register/admin")
                                .hasAuthority("ADMIN")
                                .requestMatchers("/secured/**")
                                .hasAnyAuthority("CUSTOMER", "USER", "ADMIN", "SUPER")
                                .requestMatchers("/public/**")
                                .permitAll()
                                .anyRequest()
                                .permitAll())
                .formLogin(login ->
                        login.loginPage("/public/web/login")
                                .loginProcessingUrl("/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/public/web/home")
                                .failureUrl("/public/web/login?error=true")
                                .permitAll())
                .logout(logout ->
                        logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/public/web/home")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .build();
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        return customUserDetails;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       PasswordEncoderProvider passwordEncoderProvider)
            throws Exception {

        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(customUserDetails)
                .passwordEncoder(passwordEncoderProvider.passwordEncoder());

        return authBuilder.build();
    }

}
