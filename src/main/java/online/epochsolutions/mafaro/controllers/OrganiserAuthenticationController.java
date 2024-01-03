package online.epochsolutions.mafaro.controllers;

import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.contracts.IOrganiserAccountService;
import online.epochsolutions.mafaro.dtos.common.LoginResponse;
import online.epochsolutions.mafaro.dtos.common.CreateUserAccountRequest;
import online.epochsolutions.mafaro.dtos.host.CreateUserAccountResponse;
import online.epochsolutions.mafaro.dtos.host.UserAccountLoginRequest;
import online.epochsolutions.mafaro.exceptions.EmailFailureException;
import online.epochsolutions.mafaro.exceptions.UserAccountAlreadyExistsException;
import online.epochsolutions.mafaro.exceptions.UserNotVerifiedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mafaro/admin/user")
@RequiredArgsConstructor
public class OrganiserAuthenticationController {


    private final IOrganiserAccountService organiserAccountService;

    @PostMapping("/registration")
    public ResponseEntity<CreateUserAccountResponse> createUserAccount(@RequestBody CreateUserAccountRequest request) {

        try{
            organiserAccountService.userRegistration(request);
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
    @PostMapping("/verify")
    public ResponseEntity verifyEmail(@RequestParam String token){
        if (organiserAccountService.verifyUser(token)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> userLogin(@RequestBody UserAccountLoginRequest request) throws UserNotVerifiedException, EmailFailureException {
        String jwt;
        try{
            jwt = organiserAccountService.loginUser(request);
        } catch(UserNotVerifiedException e){
            var response = new LoginResponse();
            response.setSuccess(false);
            var reason = "USER_NOT_VERIFIED";
            if (e.isNewEmailSent()){
                reason += "EMAIL_RESENT";
            }
            response.setFailureReason(reason);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (EmailFailureException e ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if(jwt == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            organiserAccountService.loginUser(request);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setJwt(jwt);
            loginResponse.setSuccess(true);
            return ResponseEntity.ok(loginResponse);
        }
    }

    //TODO : Add logout feature
}
