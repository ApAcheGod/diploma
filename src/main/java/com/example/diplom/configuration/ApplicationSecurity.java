package com.example.diplom.configuration;

import com.example.diplom.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
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


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .cors()
//                .configurationSource(corsConfigurationSource())
//                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/").authenticated()
                .anyRequest().permitAll()
                .and().httpBasic().authenticationEntryPoint(apiAwareLoginUrlAuthenticationEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        http.cors()
//                .configurationSource(corsConfigurationSource())
//                .and()
//                .csrf().disable()
////                .cors().and()
//                .authorizeRequests()
////                .antMatchers("/api/**").authenticated()
//                .antMatchers("/api/login").permitAll()
//                .antMatchers("/api/check").permitAll()
//                .antMatchers("/api/**").authenticated()
//                .antMatchers("/main").hasAnyRole("ADMIN")
//                .anyRequest().permitAll()
////                .and()
////                .formLogin()
////                .loginProcessingUrl("/login")
////                    .loginPage("  /login.html")
////                    .loginProcessingUrl("/login")
////                    .successHandler(myAuthenticationSuccessHandler())
////                    .permitAll()
//                .and().httpBasic().authenticationEntryPoint(apiAwareLoginUrlAuthenticationEntryPoint())
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }


    @Override
    public void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public ApiBasicAuthenticationEntryPoint apiAwareLoginUrlAuthenticationEntryPoint() {
        ApiBasicAuthenticationEntryPoint entryPoint = new ApiBasicAuthenticationEntryPoint();
        entryPoint.setRealmName("Api Server");
        return entryPoint;
    }

    public static class ApiBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration source = new CorsConfiguration();
        source.setAllowedOrigins(List.of("*"));
        source.setAllowedMethods(List.of("HEAD", "GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS"));
        source.setAllowCredentials(false);
        source.addAllowedOrigin("*");
        source.addAllowedHeader("*");
        source.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource con = new UrlBasedCorsConfigurationSource();
        con.registerCorsConfiguration("/**", source);
        return con;
    }


//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONAL"));
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        final CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(ImmutableList.of("*"));
//        configuration.setAllowedMethods(ImmutableList
//                .of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
//        configuration.setAllowCredentials(false);
//        configuration.setAllowedHeaders(ImmutableList.of("*"));
//        configuration.setExposedHeaders(ImmutableList
//                .of("X-Auth-Token","Authorization","Access-Control-Allow-Origin","Access-Control-Allow-Credentials"));
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }


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
//        bean.setOrder(0);
//        return bean;
//    }
}