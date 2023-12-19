package online.epochsolutions.mafaro.contracts;

import online.epochsolutions.mafaro.dtos.common.CreateUserAccountRequest;
import online.epochsolutions.mafaro.dtos.user.UserAccountLoginRequest;
import online.epochsolutions.mafaro.exceptions.EmailFailureException;
import online.epochsolutions.mafaro.exceptions.UserAccountAlreadyExistsException;
import online.epochsolutions.mafaro.exceptions.UserNotVerifiedException;
import org.springframework.transaction.annotation.Transactional;

public interface IAccountService {
    void userRegistration(CreateUserAccountRequest request) throws UserAccountAlreadyExistsException, EmailFailureException;

    String loginUser(UserAccountLoginRequest request) throws EmailFailureException, UserNotVerifiedException;

    @Transactional
    boolean verifyUser(String token);
}
