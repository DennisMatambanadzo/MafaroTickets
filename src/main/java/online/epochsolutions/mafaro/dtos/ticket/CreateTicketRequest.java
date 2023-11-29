package online.epochsolutions.mafaro.dtos.ticket;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;

@Data
public class CreateTicketRequest {

   private String eventId;

   private Integer numberOfTickets;
   private String section;
}
