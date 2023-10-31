package com.example.petclinic.web.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class BasicSecurityConfiguration
 */
@Configuration
@EnableWebSecurity
class BasicSecurityConfiguration {
    @Throws(Exception::class)
    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests()
            .requestMatchers("/**").permitAll()
            .and()
            .csrf().disable()
            .formLogin().disable()
        return http.build()
    }
}

