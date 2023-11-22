package online.epochsolutions.mafaro.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Ticket {

    @Id
    private String id;
    private String name;
    private String section;
    private LocalDateTime startTime;
}
