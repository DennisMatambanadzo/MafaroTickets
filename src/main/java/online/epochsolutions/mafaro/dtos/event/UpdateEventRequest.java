package online.epochsolutions.mafaro.dtos.event;

import lombok.Data;
import online.epochsolutions.mafaro.models.Section;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class UpdateEventRequest {
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String eventDescription;
    private Integer ageLimit;
    private String location;
    private ArrayList<Section> sections;
}
