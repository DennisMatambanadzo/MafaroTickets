package online.epochsolutions.mafaro.config;

import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.authentication.PatronJWTRequestFilter;
import online.epochsolutions.mafaro.authentication.OrganiserJWTRequestFilter;
import online.epochsolutions.mafaro.authentication.AccountJWTService;
import online.epochsolutions.mafaro.repos.PatronAccountRepository;
import online.epochsolutions.mafaro.repos.OrganiserAccountRepository;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class ApplicationFilterConfiguration {

    private final AccountJWTService accountJwtService;
    private final OrganiserAccountRepository organiserAccountRepository;
    private final PatronAccountRepository patronAccountRepository;

    @Bean
    FilterRegistrationBean<OrganiserJWTRequestFilter> jwtOrganiserRequestFilterRegistrationBean(){
        FilterRegistrationBean<OrganiserJWTRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new OrganiserJWTRequestFilter(accountJwtService, organiserAccountRepository));
        registrationBean.setOrder(-1);
        registrationBean.setName("userJWTFilter");
        registrationBean.setUrlPatterns(Collections.singletonList("/mafaro/admin/*"));
        return registrationBean;
    }

    @Bean
    FilterRegistrationBean<PatronJWTRequestFilter> jwtPatronRequestFilterRegistrationBean(){
        FilterRegistrationBean<PatronJWTRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new PatronJWTRequestFilter(accountJwtService,patronAccountRepository));
        registrationBean.setOrder(-1);
        registrationBean.setName("patronJWTFilter");
        registrationBean.setUrlPatterns(Collections.singletonList("/mafaro/patron/*"));
        return registrationBean;
    }
}
