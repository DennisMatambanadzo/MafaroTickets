package online.epochsolutions.mafaro.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import online.epochsolutions.mafaro.models.BaseUser;
import online.epochsolutions.mafaro.models.Ticket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class AccountJWTService implements AlgorithmKey {
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;

    private Algorithm algorithm;
    public static final String EMAIL_KEY = "EMAIL";
    public static final String ROLE_KEY = "ROLES";



    @Override
    @PostConstruct
    public void postConstruct(){
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generateBaseUserJWT(BaseUser user){
        return JWT.create()
                .withClaim(EMAIL_KEY,user.getEmail())
                .withClaim(ROLE_KEY, user.getRole().ordinal())
                .withExpiresAt(new Date(System.currentTimeMillis()+(1000 + expiryInSeconds)))
                .withIssuer(issuer)
                .sign(algorithm);
    }



    public String getEmail(String token){
        return JWT.decode(token).getClaim(EMAIL_KEY).asString();
    }
}
