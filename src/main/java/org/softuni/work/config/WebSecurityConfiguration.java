package org.softuni.work.config;

import org.softuni.work.areas.workers.services.interfaces.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.hibernate.criterion.Restrictions.and;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final WorkerService workerService;

    private static final String[] routes = {"/", "/about-us", "/business", "/career",
                    "/career-register",  "/career-login", "/business-register",
                    "/business-login", "/business-home","/business-logout", "/business-create-app", "/business-my-apps"};



    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfiguration(WorkerService workerService, UserDetailsService userDetailsService) {
        this.workerService = workerService;
        this.userDetailsService = userDetailsService;
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
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(routes).permitAll()
                .antMatchers("/career-home").hasAuthority("ROLE_WORKER")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/career-login").permitAll()
                .passwordParameter("password")
                .usernameParameter("email")
                .defaultSuccessUrl("/career-home-for-noobs")
                .failureUrl("/career-error")
                .and()
                .userDetailsService(this.userDetailsService);


    }
}

