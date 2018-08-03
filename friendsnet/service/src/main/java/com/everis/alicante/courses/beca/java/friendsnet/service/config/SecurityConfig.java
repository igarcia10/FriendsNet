package com.everis.alicante.courses.beca.java.friendsnet.service.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] WHITELIST = {

    		"/",
    		"/console/**",
    		"/h2-console/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/persons",
            "/persons/**",
            "/persons/**/relate",
            "/posts",
            "/posts/person/**",
            "/posts/**/person/**/like/**"
            
    };
    
    private static final String[] POST_WHITELIST = {

            "/persons/**",
            "/persons/**/relate",
            "/posts",
            "/posts/**/person/**/like/**"
            
    };
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(WHITELIST).permitAll();
        
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, POST_WHITELIST)
        	.and().ignoring().antMatchers(HttpMethod.DELETE, "/posts/**");
    }
}


