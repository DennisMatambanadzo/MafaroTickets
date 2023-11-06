package online.epochsolutions.mafaro.models;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class Event {

    @BsonId
    private String id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String eventDescription;
    private Integer ageLimit;
    private String location;
}
