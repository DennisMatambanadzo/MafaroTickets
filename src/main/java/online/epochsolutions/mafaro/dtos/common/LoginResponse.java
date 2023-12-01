package online.epochsolutions.mafaro.dtos.common;

import lombok.Data;

@Data
public class LoginResponse {

    private boolean success;
    private String jwt;
}
