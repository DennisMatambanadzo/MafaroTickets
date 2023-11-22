package online.epochsolutions.mafaro.dtos;

import lombok.Data;

@Data
public class CreateTicketRequest {

   private String eventId;
   private Integer numberOfTickets;
   private String section;
}
