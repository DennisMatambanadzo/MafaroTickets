package online.epochsolutions.mafaro.dtos.patron;

import lombok.Data;

@Data
public class CreatePatronAccountResponse {
    private String email;
    private String firstName;
    private String message;
}
