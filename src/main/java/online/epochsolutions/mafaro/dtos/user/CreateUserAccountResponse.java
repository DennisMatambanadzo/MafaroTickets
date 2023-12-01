package online.epochsolutions.mafaro.dtos.user;

import lombok.Data;

@Data
public class CreateUserAccountResponse {
    private String email;
    private String firstName;
    private String message;
}
