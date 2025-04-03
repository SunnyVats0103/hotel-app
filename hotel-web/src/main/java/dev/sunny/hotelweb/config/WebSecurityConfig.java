package dev.sunny.hotelweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public DefaultSecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/web/home",
                                        "/web/hotels/list",
                                        "/web/hotels/details/**",
                                        "/web/rooms/list",
                                        "/web/rooms/details/**",
                                        "/web/login",
                                        "/web/logout",
                                        "/webjars/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(login -> login.loginPage("/web/login")
                        .loginProcessingUrl("/web/login")
                        .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                        .failureForwardUrl("/web/login?error=true")
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/web/logout")
                        .logoutSuccessUrl("/web/home")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true))
                .build();
    }

}
