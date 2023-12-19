package online.epochsolutions.mafaro.dtos.common;

import lombok.Data;

@Data
public class CreateUserAccountRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
