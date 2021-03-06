package com.lia.system.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;


/**
 * security核心配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private TokenFilter tokenFilter;
    @Autowired
    private ExceptionHandler exceptionHandler;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 解决security跨域冲突问题
                .cors().and()
                .csrf().disable()
                // 不适用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对preflight预检测请求放行
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // 登录接口不需要认证
                .antMatchers("/system/user/login").permitAll()
                // 剩余的所有接口都需要认证
                .anyRequest().authenticated();
        // 配置token解析过滤器
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 配置认证失败和权限不足的异常处理器
        http.exceptionHandling()
                .authenticationEntryPoint(exceptionHandler)
                .accessDeniedHandler(exceptionHandler);
    }
}
