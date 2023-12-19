package online.epochsolutions.mafaro.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Document
public class Ticket {

    @Id
    private String id;
    private String name;
    private String section;
    private LocalDateTime startTime;
    private Timestamp createdAt;
    private String purchasedBy;
    private String TicketToken;
}
