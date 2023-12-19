package online.epochsolutions.mafaro.models;

import lombok.Data;

@Data
public class Section {

    private String name;
    private Long initialTicketSlots;
    private Long availableTicketSlots;
    private Integer price;

}
