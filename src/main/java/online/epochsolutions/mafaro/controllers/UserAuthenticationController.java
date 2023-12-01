package online.epochsolutions.mafaro.controllers;

import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.dtos.common.LoginResponse;
import online.epochsolutions.mafaro.dtos.user.CreateUserAccountRequest;
import online.epochsolutions.mafaro.dtos.user.CreateUserAccountResponse;
import online.epochsolutions.mafaro.dtos.user.UserAccountLoginRequest;
import online.epochsolutions.mafaro.exceptions.EmailFailureException;
import online.epochsolutions.mafaro.exceptions.UserAccountAlreadyExistsException;
import online.epochsolutions.mafaro.services.UserAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mafaro/admin/user")
@RequiredArgsConstructor
public class UserAuthenticationController {

    private final UserAccountService userAccountService;

    @PostMapping("/registration")
    public ResponseEntity<CreateUserAccountResponse> createUserAccount(@RequestBody CreateUserAccountRequest request) throws UserAccountAlreadyExistsException {

        try{
            userAccountService.createUser(request);
            CreateUserAccountResponse response = new CreateUserAccountResponse();
            response.setFirstName(request.getFirstName());
            response.setEmail(request.getEmail());
            response.setMessage(request.getFirstName() +", you have been registered with the email address: " + response.getEmail());
            return ResponseEntity.ok(response);
        }
        catch (UserAccountAlreadyExistsException | EmailFailureException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> userLogin(@RequestBody UserAccountLoginRequest request){
        String jwt = null;

        jwt= userAccountService.loginUser(request);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwt(jwt);
        loginResponse.setSuccess(jwt != null);

        return ResponseEntity.ok(loginResponse);
    }
}
