package com.tjoeun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	//	@Bean : Spring Framework가 서버가 올라갈 때 메모리에 미리 객체를 생성함
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.formLogin()
			.loginPage("/member/login")
			.defaultSuccessUrl("/")
			.usernameParameter("email")
			.failureUrl("/member/login/error")
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
			.logoutSuccessUrl("/");

		
		http.authorizeHttpRequests()
			.mvcMatchers("/", "/member/**", "/item/**").permitAll()
			.mvcMatchers("/css/**", "/js/**", "/images/**").permitAll()
			.mvcMatchers("/fragments/**", "/layouts/**", "/test/**").permitAll()
			.mvcMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().authenticated();
			
		
		http.exceptionHandling()
			.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
			
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();
	}
	
}
