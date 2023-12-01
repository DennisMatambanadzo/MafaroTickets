package online.epochsolutions.mafaro.dtos.user;

import lombok.Data;

@Data
public class UserAccountLoginRequest {

    private String email;
    private String password;
}
