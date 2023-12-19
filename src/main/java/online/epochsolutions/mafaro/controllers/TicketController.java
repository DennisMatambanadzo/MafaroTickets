package online.epochsolutions.mafaro.controllers;

import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.dtos.ticket.CreateTicketsRequest;
import online.epochsolutions.mafaro.dtos.common.DeleteResponse;
import online.epochsolutions.mafaro.exceptions.EmailFailureException;
import online.epochsolutions.mafaro.models.Patron;
import online.epochsolutions.mafaro.models.Ticket;
import online.epochsolutions.mafaro.contracts.ITicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mafaro/patron/tickets")
public class TicketController {

    private final ITicketService ITicketService;


    @PostMapping("/purchaseTicket")
    public ResponseEntity purchaseTicket(@RequestBody CreateTicketsRequest request, @AuthenticationPrincipal Patron patron) throws EmailFailureException {

       return  ITicketService.generateTicket(request,patron).isEmpty() ? ResponseEntity.internalServerError().build() : ResponseEntity.ok().build();
    }

    @GetMapping("/listTickets")
    public ResponseEntity<List<Ticket>> listAllTickets(){
        return ResponseEntity.ok(ITicketService.fetchAllTickets());
    }

    @GetMapping("/getTicket/{id}")
    public ResponseEntity<Ticket> getTicket(@PathVariable String id){
        return ResponseEntity.ok(ITicketService.getTicket(id));
    }

    @DeleteMapping("/deleteTicket/{id}")
    public ResponseEntity<DeleteResponse> deleteTicket(@PathVariable String id){
        DeleteResponse response = new DeleteResponse();
        if (ITicketService.destroyTicket(id)){
            response.setMessage("Ticket deleted");
        }else {
            response.setMessage("Delete failed! Event does not exist");
        }
        return ResponseEntity.ok(response);
    }
}
