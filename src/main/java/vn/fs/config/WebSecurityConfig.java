package vn.fs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import vn.fs.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final UserDetailsServiceImpl userDetailService;

	public WebSecurityConfig(UserDetailsServiceImpl userDetailService) {
		this.userDetailService = userDetailService;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userDetailService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

	// Dùng cho chỗ nào cần inject AuthenticationManager (nếu có)
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable());

		// đăng ký DaoAuthenticationProvider
		http.authenticationProvider(authenticationProvider());

		// rất quan trọng: khai báo UserDetailsService cho Security
		http.userDetailsService(userDetailService);

		http
				.authorizeHttpRequests(auth -> auth
						// Admin page
						.requestMatchers("/admin/**").hasRole("ADMIN")
						// Checkout cần ROLE_USER
						.requestMatchers("/checkout").hasRole("USER")
						// Các URL còn lại cho phép truy cập
						.requestMatchers("/**").permitAll()
						.anyRequest().authenticated()
				)
				.formLogin(form -> form
						.loginProcessingUrl("/doLogin")
						.loginPage("/login")
						.defaultSuccessUrl("/?login_success", true)
						.successHandler(new SuccessHandler())
						.failureUrl("/login?error=true")
						.permitAll()
				)
				.logout(logout -> logout
						.invalidateHttpSession(true)
						.clearAuthentication(true)
						.logoutUrl("/logout")                 // <– thay cho AntPathRequestMatcher
						.logoutSuccessUrl("/?logout_success")
						.permitAll()
				)
				.rememberMe(remember -> remember
						.rememberMeParameter("remember")
						// *** Fix lỗi: gắn userDetailsService cho remember-me ***
						.userDetailsService(userDetailService)
						// key cố định để token remember-me ổn định
						.key("getabec-remember-me-secret-key")
				);

		return http.build();
	}
}
