package by.it.academy.blockchain.configuration;

import by.it.academy.blockchain.configuration.jwt.JwtAuthenticationEntryPoint;
import by.it.academy.blockchain.configuration.jwt.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    JwtFilter jwtFilter;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler myAuthenticationFailureHandler() {
        return new MySimpleAuthenticationFailureHandler();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
//                .httpBasic().disable()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                .authorizeRequests()
                //Доступ только для незарегестрированных пользователей
                .antMatchers("/registration/**").not().fullyAuthenticated()
                //Доступ только для пользователей с ролью Юзер и Админ
                .antMatchers("/users/**").hasAnyRole("USER", "ADMIN") // Делаем /user/{id}/**
                //Доступ только для пользователей с ролью Администратор
                .antMatchers("/admin/**").hasRole("ADMIN")
                //Доступ разрешен всем пользователей
                .antMatchers("/", "/static/**").permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
                .and()
//                    .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .and()
//                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                    //Настройка для входа в систему
                .formLogin()
                .loginPage("/login")
//                    .failureUrl("/login")
                //Перенарпавление на главную страницу после успешного входа
                .successHandler(myAuthenticationSuccessHandler())
                .failureHandler(myAuthenticationFailureHandler())
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/");
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(jwtUserDetailsService);
        return authenticationProvider;
    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
//    }


//    @Bean
//    public FilterRegistrationBean<JwtFilter> jwtFilterFilterRegistrationBean () {
//        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(jwtFilter);
//        registrationBean.addUrlPatterns("/users/*", "/admin/*");
//        return registrationBean;
//    }

}
