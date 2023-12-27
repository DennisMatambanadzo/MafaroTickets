package online.epochsolutions.mafaro.dtos.host;

import lombok.Data;

@Data
public class CreateUserAccountResponse {
    private String email;
    private String firstName;
    private String message;
}
