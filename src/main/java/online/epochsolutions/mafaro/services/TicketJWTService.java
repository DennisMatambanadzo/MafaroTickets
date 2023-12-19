package online.epochsolutions.mafaro.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import online.epochsolutions.mafaro.models.Ticket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TicketJWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;

    private Algorithm algorithm;

    public static final String TICKET_KEY = "USERS";
    public static final String RANDOM_TICKET_VALUE = "RTV";

    @PostConstruct
    private void postConstruct(){
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generateTicketToken(Ticket ticket){
        return JWT.create()
                .withClaim(TICKET_KEY,ticket.getPurchasedBy())
                .withClaim(RANDOM_TICKET_VALUE,Math.random())
                .withExpiresAt(new Date(System.currentTimeMillis()+(1000 + expiryInSeconds)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

}
