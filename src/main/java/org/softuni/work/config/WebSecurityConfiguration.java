package org.softuni.work.config;

import org.softuni.work.areas.workers.services.interfaces.WorkerService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final WorkerService workerService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfiguration(WorkerService workerService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.workerService = workerService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/css/**", "/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                    .authorizeRequests()
                    .antMatchers("/", "/about-us", "/business", "/career", "/career-register",  "/career-login", "/business-register", "/business-login").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .csrf().disable();

        }
    }

