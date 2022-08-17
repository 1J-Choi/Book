package com.megaIT.book.springboot.config.auth;

import com.megaIT.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .headers().frameOptions().disable() // H2-console 화면을 사용하기 위해 해당 옵션들을 disable
                .and()
                .authorizeRequests()// URL 별 권한 관리를 설정하는 옵션
                .antMatchers("/","/css/**","images/**","/js/**","h2-console/**","/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // 권한 관리 대상을 지정하는 옵션 (URL, HTTP 메소드별로 관리 가능)
                .and() // anyRequest() 설정한 값들 이외 나머지 URL 들에 대한 옵션 설정
                .logout().logoutSuccessUrl("/")
                .and()
                .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
                // oauth2Login() Oauth2 로그인 기능 시작점
                // userInfoEndpoint() 로그인 성공 이후 사용자 정보를 가져올 때의 설정
                // userService(customOAuth2UserService) 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 구현체 적용
    }
}
