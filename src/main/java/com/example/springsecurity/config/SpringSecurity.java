package com.example.springsecurity.config;

import com.example.springsecurity.component.JwtAuthenticationTokenFilter;
import com.example.springsecurity.component.RestAuthenticationEntryPoint;
import com.example.springsecurity.component.RestfulAccessDeniedHandler;
import com.example.springsecurity.dto.AdminUserDetails;
import com.example.springsecurity.entity.User;
import com.example.springsecurity.entity.UserPermission;
import com.example.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/user/login", "/user/register") // ????????????????????????????????????
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS) // ????????????????????????OPTIONS??????
                .permitAll()
                .antMatchers("/**") // ?????????????????????????????????
                .permitAll()
                .anyRequest() // ????????????????????????????????????????????????
                .authenticated();
        // ????????????
        httpSecurity.headers().cacheControl();
        // ??????JWT filter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        // ????????????????????????????????????????????????
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public UserDetailsService userDetailsService() {
    // ????????????????????????
      return username -> {
        User admin = userService.queryUserByUsername(username);
        if (admin != null) {
          List<UserPermission> permissionList = userService.queryUserPermissionById(admin.getId());
          return new AdminUserDetails(admin, permissionList);
        }
        throw new UsernameNotFoundException("????????????????????????");
      };
    }
}
