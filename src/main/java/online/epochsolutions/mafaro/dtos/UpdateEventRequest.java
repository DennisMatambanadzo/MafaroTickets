package online.epochsolutions.mafaro.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateEventRequest {
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String eventDescription;
    private Integer ageLimit;
    private String location;
}
