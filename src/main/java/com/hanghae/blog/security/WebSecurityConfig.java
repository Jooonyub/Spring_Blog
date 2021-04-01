package com.hanghae.blog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
                //.antMatchers("/static/**").permitAll()
                // image 폴더를 login 없이 허용
                .antMatchers("/images/**").permitAll()
                // css 폴더를 login 없이 허용
                .antMatchers("/css/**").permitAll()
                // js 폴더를 login 없이 허용
                .antMatchers("/js/**").permitAll()
                //회원가입 프로세스 확인
                .antMatchers("/user/**").permitAll()
                //H2 콘솔 허용
                .antMatchers("/h2-console/**").permitAll()
                //비회원 게시물 전체 조회 허용
                .antMatchers("/").permitAll()
                .antMatchers("/api/articles").permitAll()
                //댓글달기 허용
                .antMatchers("/api/comments/**").permitAll()
                //비회원 상세보기 허용
                .antMatchers("/detail").permitAll()
                .antMatchers("/api/detail/**").permitAll()
                .antMatchers("/api/comment/**").permitAll()

                // 그 외 모든 요청은 인증과정 필요
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .loginProcessingUrl("/user/login")
                .failureUrl("/user/login/error") // 로그인이 실패되었을때 해당되는 URL로 요청
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/user/forbidden");

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
