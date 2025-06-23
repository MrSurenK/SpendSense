package com.MrSurenK.SpendSense_BackEnd.configuration;

import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityComponentsConfig {

    private final UserAccountRepo userAccountRepo;

    public SecurityComponentsConfig(UserAccountRepo userAccountRepo){
        this.userAccountRepo = userAccountRepo;
    }

    //Define implementation logic when functional interface is called in application
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userAccountRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    //When PasswordEncoder interface is called, Bcrypt implementation will be used
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Expose AuthenticationManager to customize
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        //DaoAuthentication Provider will be used to get user detail from Db
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        //Tells provider how to retrieve user
        authProvider.setUserDetailsService(userDetailsService());
        //Tells provider which encoder to use to decrypt password
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

}
