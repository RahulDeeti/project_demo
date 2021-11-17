package com.mydiaryapplication.userdiary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private AuthenticationSuccessHandler authenticationSuccessHandler;

    //A DataSource is a factory for connections to any physical data source.
    @Autowired
    private DataSource dataSource;

    @Autowired
    public SecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler)
    {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    /*
    Spring's Security DaoAuthenticationProvider is a simple authentication provider
    that uses a Data Access Object (DAO) to retrieve user information from a relational database.
    It leverages a UserDetailsService (as a DAO) in order to lookup the username, password and GrantedAuthoritys.
     */

    @Bean
    public DaoAuthenticationProvider authenticationProvider()
    {
       DaoAuthenticationProvider authProvider =
               new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailsService());

             authProvider.setPasswordEncoder(passwordEncoder());

             return authProvider;
    }

    //authentication failure handler


    @Autowired
   public void configureBothAuthentication(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication()
                .withUser("Admin@1")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN");
                auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/user").authenticated()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/diarypage/**").authenticated()
                .and()
                .formLogin().loginPage("/login")
                .successHandler(authenticationSuccessHandler)
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);
    }

}
