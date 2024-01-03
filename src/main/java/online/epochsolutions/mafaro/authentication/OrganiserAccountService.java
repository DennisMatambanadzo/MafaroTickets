package online.epochsolutions.mafaro.authentication;

import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.contracts.IOrganiserAccountService;
import online.epochsolutions.mafaro.dtos.common.CreateUserAccountRequest;
import online.epochsolutions.mafaro.dtos.host.UserAccountLoginRequest;
import online.epochsolutions.mafaro.exceptions.EmailFailureException;
import online.epochsolutions.mafaro.exceptions.UserAccountAlreadyExistsException;
import online.epochsolutions.mafaro.exceptions.UserNotVerifiedException;
import online.epochsolutions.mafaro.models.Role;
import online.epochsolutions.mafaro.models.Organiser;
import online.epochsolutions.mafaro.repos.OrganiserAccountRepository;
import online.epochsolutions.mafaro.repos.VerificationTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganiserAccountService implements IOrganiserAccountService {

    private final OrganiserAccountRepository organiserAccountRepository;
    private final PasswordEncryptionService passwordEncryptionService;
    private final AccountVerificationEmailService accountVerificationEmailService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final AccountJWTService accountJwtService;

    @Override
    public void userRegistration(CreateUserAccountRequest request) throws UserAccountAlreadyExistsException, EmailFailureException {
            checkUser(request);

            var user = new Organiser();

            user.setEmail(request.getEmail());
            user.setLastName(request.getLastName());
            user.setFirstName(request.getFirstName());
            user.setRole(Role.HOST);
            user.setPassword(passwordEncryptionService.encryptPassword(request.getPassword()));
            VerificationToken verificationToken = createVerificationToken(user);
            accountVerificationEmailService.sendVerificationEmail(verificationToken);
            verificationTokenRepository.save(verificationToken);
            organiserAccountRepository.save(user);
    }

    @Override
    public String loginUser(UserAccountLoginRequest request) throws EmailFailureException, UserNotVerifiedException {
        Optional<Organiser> opUser = organiserAccountRepository.findByEmailIgnoreCase(request.getEmail());
        if (opUser.isPresent()){
            Organiser user = opUser.get();
            if (passwordEncryptionService.verifyPassword(request.getPassword(), user.getPassword())){
                if (user.isEmailVerified()){
                    return accountJwtService.generateBaseUserJWT(user);
                }else {
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                    boolean resend = verificationTokens.isEmpty() ||
                            verificationTokens.get(0).getCreatedTimestamp().before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000)));
                    if (resend) {
                        VerificationToken verificationToken = createVerificationToken(user);
                        verificationTokenRepository.save(verificationToken);
                        accountVerificationEmailService.sendVerificationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);

                }
            }
        }
        return null;
    }

    @Override
    @Transactional
    public boolean verifyUser(String token){
        Optional<VerificationToken> opToken = verificationTokenRepository.findByToken(token);
        if (opToken.isPresent()){
            String email = opToken.get().getUser().getEmail();
            var user = organiserAccountRepository.findByEmailIgnoreCase(email).get();
            if (!user.isEmailVerified()){
                user.setEmailVerified(true);
                organiserAccountRepository.save(user);
                verificationTokenRepository.deleteVerificationTokenByUserEmail(user.getEmail());
                return true;
            }
        }
        return false;
    }


//    @Override
    public VerificationToken createVerificationToken(Organiser user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(accountJwtService.generateBaseUserJWT(user));
        verificationToken.setUser(user);
        verificationToken.setEmail(user.getEmail());
        verificationToken.setCreatedTimestamp(new Date(System.currentTimeMillis()));
        return verificationToken;
    }

    @Override
    public void checkUser(CreateUserAccountRequest request) throws UserAccountAlreadyExistsException {
        if (organiserAccountRepository.findByEmailIgnoreCase(request.getEmail()).isPresent()) {

            throw new UserAccountAlreadyExistsException();
        }
    }

}
