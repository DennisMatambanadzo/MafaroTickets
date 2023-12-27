package online.epochsolutions.mafaro.dtos.event;

import lombok.Data;
import online.epochsolutions.mafaro.models.Section;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class CreateEventRequest {
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private Integer ageLimit;
    private String venue;
    private ArrayList<Section> sections;

}
