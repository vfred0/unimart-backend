package ec.edu.unemi.unimart.configurations.security;

import ec.edu.unemi.unimart.configurations.jwt.JwtAuthenticationEntryPoint;
import ec.edu.unemi.unimart.configurations.jwt.JwtRequestFilter;
import ec.edu.unemi.unimart.services.auth.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService jwtUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private final PasswordEncoder passwordEncoder;
    private final LogoutService logoutService;
    private final CsrfTokenFilter csrfTokenFilter;
    //private final CsrfTokenHandler csrfTokenHandler;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //var requestHandler = new CsrfTokenRequestAttributeHandler();
        // requestHandler.setCsrfRequestAttributeName("_csrf");
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                //.httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                //.csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler)
                //.ignoringRequestMatchers("/api/v1/auth/**")
                //(      .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //)
                //.addFilterAfter(csrfTokenFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(jwtRequestFilter, BasicAuthenticationFilter.class)
//                .logout(logout ->
//                        logout.logoutUrl("/api/v1/auth/logout")
//                                .addLogoutHandler(logoutService)
//                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
//                )
                .sessionManagement(Customizer.withDefaults());

        return httpSecurity.build();
    }

}
