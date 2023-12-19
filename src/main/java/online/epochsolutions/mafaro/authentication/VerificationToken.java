package online.epochsolutions.mafaro.authentication;

import lombok.Data;
import online.epochsolutions.mafaro.models.BaseUser;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class VerificationToken {

    @Id
    private String id;
    private String token;
    private Date createdTimestamp;
    private String email;
    private BaseUser user;
}
