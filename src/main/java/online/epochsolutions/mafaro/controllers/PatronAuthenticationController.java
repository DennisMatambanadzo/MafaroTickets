package online.epochsolutions.mafaro.controllers;

import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.dtos.common.LoginResponse;
import online.epochsolutions.mafaro.dtos.patron.CreatePatronAccountResponse;
import online.epochsolutions.mafaro.authentication.PatronAccountService;
import online.epochsolutions.mafaro.dtos.user.UserAccountLoginRequest;
import online.epochsolutions.mafaro.exceptions.EmailFailureException;
import online.epochsolutions.mafaro.exceptions.UserAccountAlreadyExistsException;
import online.epochsolutions.mafaro.exceptions.UserNotVerifiedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mafaro/patron")
public class PatronAuthenticationController {

    private final PatronAccountService patronService;

    @PostMapping("/registration")
    public ResponseEntity<CreatePatronAccountResponse> patronRegistration(@RequestBody CreatePatronAccountRequest request) throws EmailFailureException, UserAccountAlreadyExistsException {
        try{
            patronService.userRegistration(request);
            CreatePatronAccountResponse response = new CreatePatronAccountResponse();
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
        if (patronService.verifyUser(token)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> userLogin(@RequestBody UserAccountLoginRequest request) throws UserNotVerifiedException, EmailFailureException {
        String jwt = null;
        try{
            jwt = patronService.loginPatron(request);
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
            patronService.loginPatron(request);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setJwt(jwt);
            loginResponse.setSuccess(true);
            return ResponseEntity.ok(loginResponse);
        }
    }

//    @PutMapping("/updatePatron")
//    public ResponseEntity<Boolean> updatePatron(@RequestBody UpdatePatronRequest request){
//        return ResponseEntity.ok(patronService.updatePatron(request));
//    }
}
