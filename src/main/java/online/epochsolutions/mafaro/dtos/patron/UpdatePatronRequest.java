package online.epochsolutions.mafaro.dtos.patron;

import lombok.Data;

@Data
public class UpdatePatronRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
