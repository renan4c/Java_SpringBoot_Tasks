package br.com.tarefas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.tarefas.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
// se precisa estar logado para acessar recurso
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	private static final String[] PATHS = new String[] {"/tarefa/**", "/categoria/**", "/usuario/**"};
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private AuthEntryPointJWT unauthorizedHandler; 
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	/**
	 * AUTENTICATION
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		/*auth.inMemoryAuthentication()
		.passwordEncoder(passwordEncoder())
		.withUser("usuario")
		.password(passwordEncoder().encode("senha"))
		.roles("ADMIN");*/
	}
	
	/**
	 * AUTORIZATION
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors()
		.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.authorizeRequests()
			.antMatchers("/api/auth/**").permitAll()
			.antMatchers(HttpMethod.POST, PATHS)
				.hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, PATHS)
				.hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE, PATHS)
				.hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, PATHS)
				.hasAnyRole("ADMIN", "USER")
			.antMatchers("/h2-console/**").permitAll()
			.anyRequest().authenticated()
		.and()
			//.httpBasic();
			.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class) // com JWT TOKEN
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
				
	}
	
}
