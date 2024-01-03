package online.epochsolutions.mafaro.authentication;

import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.models.Organiser;
import online.epochsolutions.mafaro.repos.OrganiserAccountRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


@RequiredArgsConstructor
public class OrganiserJWTRequestFilter extends OncePerRequestFilter {

    private final AccountJWTService accountJwtService;
    private final OrganiserAccountRepository accountRepository;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(7);
            try{
                String email = accountJwtService.getEmail(token);
                Optional<Organiser> opUser = accountRepository.findByEmailIgnoreCase(email);
                if (opUser.isPresent()){
                    Organiser user = opUser.get();
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }catch (JWTDecodeException ignored){

            }
        }

        filterChain.doFilter(request,response);
    }
}
