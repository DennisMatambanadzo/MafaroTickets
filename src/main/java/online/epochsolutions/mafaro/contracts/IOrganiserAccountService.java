package online.epochsolutions.mafaro.contracts;

import online.epochsolutions.mafaro.authentication.VerificationToken;
import online.epochsolutions.mafaro.dtos.common.CreateUserAccountRequest;
import online.epochsolutions.mafaro.dtos.host.UserAccountLoginRequest;
import online.epochsolutions.mafaro.exceptions.EmailFailureException;
import online.epochsolutions.mafaro.exceptions.UserAccountAlreadyExistsException;
import online.epochsolutions.mafaro.exceptions.UserNotVerifiedException;
import online.epochsolutions.mafaro.models.Organiser;

public interface IOrganiserAccountService {

    void userRegistration(CreateUserAccountRequest request) throws UserAccountAlreadyExistsException, EmailFailureException;

    String loginUser(UserAccountLoginRequest request) throws EmailFailureException, UserNotVerifiedException;

    boolean verifyUser(String token);

    VerificationToken createVerificationToken(Organiser user);

    void checkUser(CreateUserAccountRequest request) throws UserAccountAlreadyExistsException;
}
