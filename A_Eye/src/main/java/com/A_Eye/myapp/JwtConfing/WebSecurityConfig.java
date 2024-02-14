package com.A_Eye.myapp.JwtConfing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        		.cors(AbstractHttpConfigurer::disable)
                .httpBasic((basic) -> basic.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/images/**", "/api/boardList", "/api/sign-in", "/api/register", "/api/prove", "/swagger-ui.html", "/api/profile", "/api/boardList","/api/boardGet"
                        		, "/api/boardAnswer", "/api/application","/api/getMonthAds","/api/getChartAd","/api/getBarChartAd","/api/getUserAds").permitAll()
                        .antMatchers("/billing/**").hasRole("1")
                        .anyRequest().authenticated())
                .formLogin(login -> login.disable());
	        
	    return http.build();
	}
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		// --> 이미 다 만들어진 암호화 로직을 가져와서 사용하겠다.
		// 12345 ---> 78917
		// 12345 ---> abcde
	}

}