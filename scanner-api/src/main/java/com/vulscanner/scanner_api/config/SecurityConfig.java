package com.vulscanner.scanner_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.vulscanner.scanner_api.security.JwtAuthenticationFilter;
import com.vulscanner.scanner_api.security.JwtTokenProvider;




/**
 * La anotación @Bean le dice a Spring:
	“Este método devuelve un objeto que quiero que esté disponible en el 
	contexto de la aplicación para que pueda ser inyectado donde se necesite.”
	
	En el caso del @Bean de PasswordEncoder
	- ✅ Spring crea una instancia de BCryptPasswordEncoder
	- ✅ La registra como un bean en el contexto
	- ✅ Puedes inyectarla con @Autowired en cualquier clase (como tu UsuarioService)
	
	---------------------------------------------------------------------------------------
	¿Por qué funciona sin securityMatcher(...)?
	- Spring Security ya aplica el SecurityFilterChain a todas las rutas por defecto
	- Solo necesitas usar requestMatchers(...) para definir qué rutas son públicas o protegidas
	- Evitas el uso de clases deprecated y mantienes tu código limpio y futuro-proof

 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
        		.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/auth/**",
                    		"/swagger-ui/**",
                    	    "/v3/api-docs/**",
                    	    "/v3/api-docs.yaml",
                    	    "/swagger-ui.html"
                    		).permitAll() // login y registro públicos
                    .anyRequest().authenticated() // todo lo demás requiere token
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
