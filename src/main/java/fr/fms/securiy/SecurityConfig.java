package fr.fms.securiy;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	String usersByUsernameQuery = "select email, password, enable from users where email = ?";
	String authoritiesByUsernameQuery = "SELECT u.email, r.name from users AS u LEFT JOIN users_roles ur ON u.user_id = ur.users_user_id LEFT JOIN role r ON ur.roles_role_id = r.role_id where email = ?";

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = passwordEncoder();
		auth.inMemoryAuthentication().withUser("delmerie").password(passwordEncoder.encode("1234")).roles("ADMIN",
				"USER");
		auth.inMemoryAuthentication().withUser("mama").password(passwordEncoder.encode("1234")).roles("USER");
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder());

		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery(usersByUsernameQuery)
				.authoritiesByUsernameQuery(authoritiesByUsernameQuery).rolePrefix("ROLE_").passwordEncoder(passwordEncoder);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().defaultSuccessUrl("/index");
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
		http.exceptionHandling().accessDeniedPage("/403");
	}
}
