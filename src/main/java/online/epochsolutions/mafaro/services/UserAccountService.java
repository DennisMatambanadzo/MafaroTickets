package online.epochsolutions.mafaro.services;

import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.authentication.EmailService;
import online.epochsolutions.mafaro.authentication.PasswordEncryptionService;
import online.epochsolutions.mafaro.authentication.VerificationToken;
import online.epochsolutions.mafaro.dtos.user.CreateUserAccountRequest;
import online.epochsolutions.mafaro.dtos.user.UserAccountLoginRequest;
import online.epochsolutions.mafaro.exceptions.EmailFailureException;
import online.epochsolutions.mafaro.exceptions.UserAccountAlreadyExistsException;
import online.epochsolutions.mafaro.models.Role;
import online.epochsolutions.mafaro.models.User;
import online.epochsolutions.mafaro.repos.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncryptionService passwordEncryptionService;
    private final EmailService emailService;
//    private final JWTService jwtService;

    public void createUser(CreateUserAccountRequest request) throws UserAccountAlreadyExistsException, EmailFailureException {
            checkUser(request);
            var user = new User();

            user.setEmail(request.getEmail());
            user.setLastName(request.getLastName());
            user.setFirstName(request.getFirstName());
            user.setRole(Role.USER);
            user.setPassword(passwordEncryptionService.encryptPassword(request.getPassword()));
            VerificationToken verificationToken = createVerificationToken(user);
            emailService.sendVerificationEmail(verificationToken);
            userAccountRepository.save(user);

    }

    private static VerificationToken createVerificationToken(User user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken("bluuurururur");
        verificationToken.setUser(user);
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        return verificationToken;
    }

    public String loginUser(UserAccountLoginRequest request) {
        var opUser = userAccountRepository.findByEmailIgnoreCase(request.getEmail());
        if (opUser.isPresent()){
            User user = opUser.get();
            if (passwordEncryptionService.verifyPassword(request.getPassword(), user.getPassword())){
//                return jwtService.generateJWT(user);
            }
        }
        return null;
    }

    private void checkUser(CreateUserAccountRequest request) throws UserAccountAlreadyExistsException {
        if(userAccountRepository.findByEmailIgnoreCase(request.getEmail()).isPresent()){

            throw new UserAccountAlreadyExistsException();
        }


    }





}
