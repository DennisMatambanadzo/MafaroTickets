package online.epochsolutions.mafaro.authentication;

import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.authentication.EmailService;
import online.epochsolutions.mafaro.authentication.JWTService;
import online.epochsolutions.mafaro.authentication.PasswordEncryptionService;
import online.epochsolutions.mafaro.authentication.VerificationToken;
import online.epochsolutions.mafaro.dtos.user.CreateUserAccountRequest;
import online.epochsolutions.mafaro.dtos.user.UserAccountLoginRequest;
import online.epochsolutions.mafaro.exceptions.EmailFailureException;
import online.epochsolutions.mafaro.exceptions.UserAccountAlreadyExistsException;
import online.epochsolutions.mafaro.models.Role;
import online.epochsolutions.mafaro.models.User;
import online.epochsolutions.mafaro.repos.UserAccountRepository;
import online.epochsolutions.mafaro.repos.VerificationTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

//@SuppressWarnings("ALL")
@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncryptionService passwordEncryptionService;
    private final EmailService emailService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final JWTService jwtService;

    public void createUser(CreateUserAccountRequest request) throws UserAccountAlreadyExistsException, EmailFailureException {
            checkUser(request);
            var user = new User();

            user.setEmail(request.getEmail());
            user.setLastName(request.getLastName());
            user.setFirstName(request.getFirstName());
            user.setRole(Role.USER);
            user.setPassword(passwordEncryptionService.encryptPassword(request.getPassword()));
            VerificationToken verificationToken = createVerificationToken(user.getEmail(),user);
            emailService.sendVerificationEmail(verificationToken);
            verificationTokenRepository.save(verificationToken);
            userAccountRepository.save(user);
    }

    private VerificationToken createVerificationToken(String userEmail, User user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateJWT(user));
        verificationToken.setUserEmail(userEmail);
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

//    @Transactional
//    public boolean verifyUser(String token){
//        Optional<VerificationToken> opToken = verificationTokenRepository.findByToken(token);
//        if (opToken.isPresent()){
//            VerificationToken verificationToken = opToken.get();
//            var user = verificationToken.getUser();
//            if (!user.isEmailVerified()){
//                user.setEmailVerified(true);
//
//                userAccountRepository.save(user);
//                verificationTokenRepository.deleteByUser(user);
//                return true;
//            }
//        }
//        return false;
//    }

    private void checkUser(CreateUserAccountRequest request) throws UserAccountAlreadyExistsException {
        if(userAccountRepository.findByEmailIgnoreCase(request.getEmail()).isPresent()){

            throw new UserAccountAlreadyExistsException();
        }


    }





}
