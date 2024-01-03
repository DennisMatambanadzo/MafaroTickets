package online.epochsolutions.mafaro.authentication;

import online.epochsolutions.mafaro.dtos.common.CreateUserAccountRequest;
import online.epochsolutions.mafaro.exceptions.UserAccountAlreadyExistsException;
import online.epochsolutions.mafaro.models.Organiser;
import online.epochsolutions.mafaro.models.Role;
import online.epochsolutions.mafaro.repos.OrganiserAccountRepository;
import online.epochsolutions.mafaro.repos.VerificationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrganiserAccountServiceTest {



    @Mock
    private OrganiserAccountRepository organiserAccountRepository;
    @Mock
    private PasswordEncryptionService passwordEncryptionService;
    @Mock
    private AccountVerificationEmailService accountVerificationEmailService;
    @Mock
    private VerificationTokenRepository verificationTokenRepository;
    @Mock
    private AccountJWTService accountJwtService;


    @InjectMocks
    private OrganiserAccountService underTest;
    @BeforeEach
    void setUp() {
        underTest = new OrganiserAccountService(organiserAccountRepository,
                passwordEncryptionService,
                accountVerificationEmailService,
                verificationTokenRepository,
                accountJwtService);

    }

    @Test
    void userRegistration() {
        Organiser user = new Organiser();
        user.setEmail("test@test.com");
        user.setPassword(passwordEncryptionService.encryptPassword("password"));
        user.setFirstName("test");
        user.setRole(Role.HOST);
        user.setLastName("lastTest");

        user.setId("1");
        user.setContactEmail("test@test.com");
        user.setFullLegalName("Test Testor");
        user.setContactEmail("test@test.com");
        user.setPhoneNumber("12344");
//
//        VerificationToken verificationToken = new VerificationToken();
//        verificationToken.setToken(accountJwtService.generateBaseUserJWT(user));
//        verificationToken.setUser(user);
//        verificationToken.setEmail(user.getEmail());
//        verificationToken.setCreatedTimestamp(new Date(System.currentTimeMillis()));

        VerificationToken verificationToken1 = underTest.createVerificationToken(user);
//        verify(verificationToken1);
//        verify(verificationTokenRepository.save(verificationToken1));
//        verify(hostAccountRepository.save(user));


//        assertThat(hostAccountRepository.save(user)).isInstanceOfAny(BaseUser.class);

        verify(organiserAccountRepository).save(user);
    }

    @Test
    void loginUser() {
    }

    @Test
    void verifyUser() {
    }

    @Test
    void createVerificationToken() {

        Organiser user = new Organiser();
        user.setId("1");
        user.setContactEmail("test@test.com");
        user.setFullLegalName("Test Testor");
        user.setContactEmail("test@test.com");
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setFirstName("test");
        user.setLastName("lastTest");


        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(accountJwtService.generateBaseUserJWT(user));
        verificationToken.setUser(user);
        verificationToken.setEmail(user.getEmail());
        verificationToken.setCreatedTimestamp(new Date(System.currentTimeMillis()));
    }

    @Test
    void checkUser() throws UserAccountAlreadyExistsException {

        CreateUserAccountRequest request = new CreateUserAccountRequest();
        request.setEmail("test@test.com");
        request.setPassword("password");
        request.setFirstName("test");
        request.setLastName("lastTest");

        underTest.checkUser(request);

        verify(organiserAccountRepository).findByEmailIgnoreCase(request.getEmail());
    }
}