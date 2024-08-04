package com.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  // パスワードの暗号化
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  InMemoryUserDetailsManager userDetailsService() {
	  
    // 管理者設定
    UserDetails admin = User
        .withUsername("admin")
        .password(passwordEncoder().encode("admin"))
        .roles("ADMIN")
        .build();

    // ユーザー設定
    UserDetails user = User
        .withUsername("user")
        .password(passwordEncoder().encode("user"))
        .roles("USER")
        .build();
    
    return new InMemoryUserDetailsManager(admin, user);
    
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        // ログインページの許可設定
        .formLogin(login -> login
            .loginPage("/login") // ログインページのURL
            .defaultSuccessUrl("/admin/item_click") // 認証成功時のデフォルトの遷移先
            .permitAll())
        
        // ログアウト設定
        .logout(logout -> logout
            .logoutUrl("/logout") // ログアウトのURL
            .logoutSuccessUrl("/login?logout") // ログアウト成功時のリダイレクト先
            .invalidateHttpSession(true) // セッションを無効にする
            .deleteCookies("JSESSIONID") // クッキーを削除する
            .permitAll())

        // リクエストの許可設定
        .authorizeRequests(authz -> authz
        		//ログインしてない場合はアクセスできない
        		.antMatchers("/admin/**").hasRole("ADMIN")
        	    .antMatchers("/user/**").hasAnyRole("USER")
        	 // ログインページとホームページへのアクセスを許可
            .antMatchers("/", "/inquiry/**", "/login","/item/**"
            		).permitAll()
         // その他のリクエストはすべて拒否
//            .anyRequest().denyAll() 
        )
        
        // 未認証のユーザーの例外ハンドリング
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint((request, response, authException) -> 
         // ログインしていない場合のリダイレクト先
           response.sendRedirect("/"))); 
    

       
    return http.build();
  }

}
