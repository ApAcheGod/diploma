package com.example.diplom.configuration;

import com.example.diplom.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userService;


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
////                .cors()
////                .configurationSource(corsConfigurationSource())
////                .and()
//                .cors()
//                .disable()
////                .and()
//                .csrf()
//                .disable()
//                .authorizeRequests()
////                .antMatchers(HttpMethod.OPTIONS, "http://localhost:3000").permitAll()
//
//                .antMatchers("/api/login").permitAll()
//                .antMatchers("/").authenticated()
//                .anyRequest().permitAll()
//                .and().httpBasic().authenticationEntryPoint(apiAwareLoginUrlAuthenticationEntryPoint()).realmName("Api Server")
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        ;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        http.anonymous().disable()
//                .requestMatcher(new BasicRequestMatcher())
                .authorizeRequests()
                .and()
                .httpBasic()
                .authenticationEntryPoint(apiAwareLoginUrlAuthenticationEntryPoint())
                .and()
                .addFilterBefore(new BasicAuthenticationFilter(authenticationManagerBean()), BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).sessionFixation().migrateSession()
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll().anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .cors().configurationSource(request -> corsConfiguration);

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authenticationProvider());
    }

//    private static class BasicRequestMatcher implements RequestMatcher {
//        @Override
//        public boolean matches(HttpServletRequest request) {
//            String auth = request.getHeader("Authorization");
//            return (auth != null && auth.startsWith("Basic"));
//        }
//    }

    @Bean
    public ApiBasicAuthenticationEntryPoint apiAwareLoginUrlAuthenticationEntryPoint() {
        ApiBasicAuthenticationEntryPoint entryPoint = new ApiBasicAuthenticationEntryPoint();
        entryPoint.setRealmName("Api Server");
        return entryPoint;
    }

    public static class ApiBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

        @Override
        public void afterPropertiesSet() {
            super.afterPropertiesSet();
        }

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
            response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
            PrintWriter writer = response.getWriter();
            if (authException.getMessage().equals("Bad credentials")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials");
            }
            writer.println("error - " + authException.getMessage());
        }

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

//    @Bean
//    public FilterRegistrationBean corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("http://localhost:3000");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return bean;
//    }
}