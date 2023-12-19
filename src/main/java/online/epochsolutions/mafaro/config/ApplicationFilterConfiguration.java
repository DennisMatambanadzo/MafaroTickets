package online.epochsolutions.mafaro.config;

import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.authentication.PatronJWTRequestFilter;
import online.epochsolutions.mafaro.authentication.UserJWTRequestFilter;
import online.epochsolutions.mafaro.authentication.AccountJWTService;
import online.epochsolutions.mafaro.repos.PatronAccountRepository;
import online.epochsolutions.mafaro.repos.HostAccountRepository;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class ApplicationFilterConfiguration {

    private final AccountJWTService accountJwtService;
    private final HostAccountRepository userAccountRepository;
    private final PatronAccountRepository patronAccountRepository;

    @Bean
    FilterRegistrationBean<UserJWTRequestFilter> jwtUserRequestFilterRegistrationBean(){
        FilterRegistrationBean<UserJWTRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserJWTRequestFilter(accountJwtService,userAccountRepository));
        registrationBean.setOrder(-1);
        registrationBean.setName("userJWTFilter");
        registrationBean.setUrlPatterns(Collections.singletonList("/mafaro/admin/*"));
        return registrationBean;
    }

    @Bean
    FilterRegistrationBean<PatronJWTRequestFilter> jwtRequestFilterRegistrationBean(){
        FilterRegistrationBean<PatronJWTRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new PatronJWTRequestFilter(accountJwtService,patronAccountRepository));
        registrationBean.setOrder(-1);
        registrationBean.setName("patronJWTFilter");
        registrationBean.setUrlPatterns(Collections.singletonList("/mafaro/patron/*"));
        return registrationBean;
    }
}
