package online.epochsolutions.mafaro.authentication;

import lombok.Data;
import online.epochsolutions.mafaro.models.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Data
@Document
public class VerificationToken {

    @Id
    private String id;
    private String token;
    private Timestamp createdTimestamp;
    private String userEmail;
}
