package online.epochsolutions.mafaro.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import online.epochsolutions.mafaro.models.User;
import online.epochsolutions.mafaro.repos.UserAccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class JWTService {
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;

    private Algorithm algorithm;
    public static final String EMAIL_KEY = "EMAIL";
    public static final String ROLE_KEY = "ROLES";
    public static final String TICKET_KEY = "USERS";
    private final UserAccountRepository repository;

    public JWTService(UserAccountRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void postConstruct(){
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generateJWT(User user){
        return JWT.create()
                .withClaim(EMAIL_KEY,user.getEmail())

                .withExpiresAt(new Date(System.currentTimeMillis()+(1000 + expiryInSeconds)))
                .withIssuer(issuer)
                .sign(algorithm);
    }
}
