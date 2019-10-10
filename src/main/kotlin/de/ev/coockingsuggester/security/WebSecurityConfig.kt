package de.ev.coockingsuggester.security
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.*


@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .authorizeRequests().anyRequest()
                //.antMatchers("/", "/browser").permitAll()
                //.anyRequest().authenticated()
                //.and()
                //.formLogin()
                //.loginPage("/login")
                //.permitAll()
                //.and()
                //.logout()
                .permitAll()
    }

    @Bean
    public override fun userDetailsService(): UserDetailsService {
        val user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build()

        return InMemoryUserDetailsManager(user)
    }
}
