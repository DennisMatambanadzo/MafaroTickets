package online.epochsolutions.mafaro.dtos.ticket;

import lombok.Data;

@Data
public class CreateTicketsRequest {

   private String eventId;

   private Integer numberOfTickets;
   private String section;
}
