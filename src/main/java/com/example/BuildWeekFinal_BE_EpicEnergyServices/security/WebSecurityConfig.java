package com.example.BuildWeekFinal_BE_EpicEnergyServices.security;


import com.example.BuildWeekFinal_BE_EpicEnergyServices.security.jwt.AuthEntryPointJwt;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.security.jwt.AuthTokenFilter;
import com.example.BuildWeekFinal_BE_EpicEnergyServices.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity(debug = true)// Abilita la sicurezza Web di Spring Security e il debug
@EnableMethodSecurity // Abilita la protezione a livello di metodo (@PreAuthorize, @Secured)
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    AuthEntryPointJwt unauthorizedHandler;



    @Bean // Crea un filtro per intercettare le richieste HTTP e verificare il JWT.
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    // Spring crea in automatico un oggetto Password Encoder
    @Bean //Cripta le password degli utenti prima di salvarle nel database.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean //Viene utilizzato da Spring Security per verificare username e password quando un utente si autentica.
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());

        return auth;
    }

    @Bean //Viene usato dal sistema di login per gestire le richieste di autenticazione.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean //Definizione della Catena di Sicurezza. Configura il comportamento della sicurezza dell’applicazione.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) //Disabilita la protezione CSRF (Cross-Site Request Forgery). CSRF è utile nei form tradizionali, ma per le API REST con JWT non è necessario.
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler)) //Se un utente non ha i permessi giusti, riceverà una risposta 401 (Unauthorized).
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Ogni richiesta deve essere autenticata con un token JWT, senza bisogno di sessioni.
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/user/new").permitAll() // Permette la registrazione senza autenticazione
                                .requestMatchers("/user/login").permitAll() // Permette il login senza autenticazione

                                .requestMatchers("/user/auth/**").hasRole("USER")  // Accessibile solo agli utenti con ruolo USER
                                .requestMatchers("/admin/**").hasRole("ADMIN") // Accessibile solo agli ADMIN
                        // Tutte le altre richieste richiedono autenticazione
                                .anyRequest()

                                .authenticated()
                );
        //Configura il provider di autenticazione e aggiunge il filtro JWT
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
return http.build();
    }

}


