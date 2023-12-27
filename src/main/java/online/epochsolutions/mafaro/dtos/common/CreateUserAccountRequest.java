package online.epochsolutions.mafaro.dtos.common;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserAccountRequest {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String password;

}
