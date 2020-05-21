package com.qdw.config;

import com.qdw.service.UserDetailsServiceImpl;
import com.qdw.service.UserService;
import com.qdw.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.userDetailsService(userDetailsService)
                .authorizeRequests()
                .antMatchers("/static/**","/").permitAll()
                .antMatchers("/admin/**","/level1/**").hasRole("admin");
        http.formLogin()
                .loginPage("/mylogin").loginProcessingUrl("/login");

//        http.formLogin();
////        http.formLogin().loginPage("/admin/Login").loginProcessingUrl("/login");
        http.logout().logoutSuccessUrl("/index2");

        //关闭
        http.csrf().disable();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().passwordEncoder( new BCryptPasswordEncoder())
//                .withUser("qdw").password(new BCryptPasswordEncoder().encode("123321")).roles("admin");
//    }
}
