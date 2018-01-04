package vidias.virtualcloset.configuration;

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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import vidias.virtualcloset.model.Role;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/csrf", "/lib/**", "/assets/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login/login.html")
                .loginProcessingUrl("/processLogin")
                .defaultSuccessUrl("/")
                .failureUrl("/login/login.html")
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .and()
            .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
            .usersByUsernameQuery("select name, password, active from vc_user where name = ?")
            .authoritiesByUsernameQuery("select name, "
                    + "case "
                    + "when password_expiration_date is not null and password_expiration_date <= current_timestamp then '" + Role.NONE.name() + "' "
                    + "else role "
                    + "end "
                    + "from vc_user where name = ?")
            .passwordEncoder(passwordEncoder);
    }
}
