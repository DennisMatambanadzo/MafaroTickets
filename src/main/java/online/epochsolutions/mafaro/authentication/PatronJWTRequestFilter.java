package online.epochsolutions.mafaro.authentication;

import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.models.Patron;
import online.epochsolutions.mafaro.repos.PatronAccountRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


@RequiredArgsConstructor
public class PatronJWTRequestFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final PatronAccountRepository accountRepository;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(7);
            try{
                String email = jwtService.getEmail(token);
                Optional<Patron> opUser = accountRepository.findByEmailIgnoreCase(email);
                if (opUser.isPresent()){
                    Patron patron = opUser.get();
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(patron,null,patron.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }catch (JWTDecodeException ignored){

            }
        }

        filterChain.doFilter(request,response);
    }
}
