package online.epochsolutions.mafaro.dtos.user;

import lombok.Data;

@Data
public class CreateUserAccountRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
