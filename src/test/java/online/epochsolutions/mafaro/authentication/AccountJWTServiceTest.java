package online.epochsolutions.mafaro.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import online.epochsolutions.mafaro.models.BaseUser;
import online.epochsolutions.mafaro.models.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Date;

import static online.epochsolutions.mafaro.authentication.AccountJWTService.EMAIL_KEY;
import static online.epochsolutions.mafaro.authentication.AccountJWTService.ROLE_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class AccountJWTServiceTest {

    public static final int expiry = 604800;
    @Mock
    private AccountJWTService accountJWTService;

    String bigKey = "BigKey";
    Algorithm algorithm = Algorithm.HMAC256(bigKey);


    String mafaro = "mafaro";

    @BeforeEach
    void setUp(){
        accountJWTService = new AccountJWTService();
        accountJWTService.setAlgorithm(algorithm);
        accountJWTService.setIssuer(mafaro);
        accountJWTService.setExpiryInSeconds(expiry);
        accountJWTService.setAlgorithmKey(bigKey);
    }

    @Test
    void generateBaseUserJWT() {



        BaseUser user = new BaseUser();
        user.setFirstName("test");
        user.setFirstName("test");
        user.setEmail("test@test.com");
        user.setRole(Role.HOST);
        user.setPassword("password");

        String token = JWT.create()
                .withClaim(EMAIL_KEY, user.getEmail())
                .withClaim(ROLE_KEY, user.getRole().ordinal())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 + expiry)))
                .withIssuer(mafaro)
                .sign(algorithm);


        assertThat(token).startsWith("ey");
    }

    @Test
    void getEmail() {
        BaseUser user = new BaseUser();
        user.setFirstName("test");
        user.setFirstName("test");
        user.setEmail("test@test.com");
        user.setRole(Role.HOST);
        user.setPassword("password");

        String token = accountJWTService.generateBaseUserJWT(user);

        assertThat(accountJWTService.getEmail(token)).isEqualTo("test@test.com");


    }
}