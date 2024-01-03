package online.epochsolutions.mafaro.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Document
@Data
public class Event {

    @BsonId
    private String id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private Integer ageLimit;
    private String venue;
    private String salesPitch;
    private String performers;
    private String category;
 /*   private String image;
    private String logo;*/
    @JsonIgnore
    private Organiser createdBy;
    private ArrayList<Section> sections;
}
