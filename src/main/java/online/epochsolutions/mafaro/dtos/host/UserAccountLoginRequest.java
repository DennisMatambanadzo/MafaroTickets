package online.epochsolutions.mafaro.dtos.host;

import lombok.Data;

@Data
public class UserAccountLoginRequest {

    private String email;
    private String password;
}
