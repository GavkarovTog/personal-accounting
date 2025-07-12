package com.example.personal_accounting;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.annotation.SessionScope;

import com.example.personal_accounting.accounts_and_categories.account.validation.AccountCreationFormValidator;
import com.example.personal_accounting.accounts_and_categories.account.validation.AccountEditionFormValidator;
import com.example.personal_accounting.security.SecurityService;
import com.example.personal_accounting.security.validation.RegistrationFormValidator;
import com.example.personal_accounting.settings.SettingsRedirectionFilter;
import com.example.personal_accounting.settings.UserDataInitializingAuthenticationSuccessHandler;
import com.example.personal_accounting.settings.UserSettingsHolder;
import com.example.personal_accounting.user_specific.UserNumber;

@Configuration
@EnableWebSecurity
public class ApplicationConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/login").permitAll()
                .requestMatchers("/register").permitAll()
                .requestMatchers("/css/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(login -> 
                login
                    .loginPage("/login")
                    .successHandler(userSettingsAwareAuthenticationSuccessHandler()))
            .requestCache(requestCache -> requestCache.disable())
            .addFilterAfter(settingsRedirectionFilter(), AuthorizationFilter.class);

        return http.build();
    }

    @Bean
    public SettingsRedirectionFilter settingsRedirectionFilter() {
        return new SettingsRedirectionFilter();
    }

    @Bean
    public UserDataInitializingAuthenticationSuccessHandler userSettingsAwareAuthenticationSuccessHandler() {
        return new UserDataInitializingAuthenticationSuccessHandler();
    }

    @Bean
    public UserDetailsManager userDetailsService() {
        return new JdbcUserDetailsManager(dataSource());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .url("jdbc:postgresql://localhost:5432/personal-accounting")
            .username("postgres")
            .password("123456")
            .build();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource());
        return validatorFactoryBean;
    }

    @Bean
    public SecurityService securityService() {
        return new SecurityService();
    }

    @Bean
    public RegistrationFormValidator registrationFormValidator() {
        return new RegistrationFormValidator();
    }

    @Bean
    public AccountCreationFormValidator accountCreationFormValidator() {
        return new AccountCreationFormValidator();
    }

    @Bean
    public AccountEditionFormValidator accountEditionFormValidator() {
        return new AccountEditionFormValidator();
    }

    @SessionScope
    @Bean
    public UserSettingsHolder userSettingsHolder() {
        return new UserSettingsHolder();
    }

    @SessionScope
    @Bean
    public UserNumber userNumber() {
        return new UserNumber();
    }
}
