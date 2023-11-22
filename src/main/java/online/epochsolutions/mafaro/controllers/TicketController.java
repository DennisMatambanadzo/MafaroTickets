package online.epochsolutions.mafaro.controllers;

import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.dtos.CreateTicketRequest;
import online.epochsolutions.mafaro.dtos.DeleteResponse;
import online.epochsolutions.mafaro.models.Event;
import online.epochsolutions.mafaro.models.Ticket;
import online.epochsolutions.mafaro.services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mafaro/tickets")
public class TicketController {

    private final TicketService ticketService;


    @PostMapping("/purchaseTicket")
    public ResponseEntity<Integer> purchaseTicket(@RequestBody CreateTicketRequest request){
       return ResponseEntity.ok( ticketService.generateTicket(request));
    }

    @GetMapping("/listTickets")
    public ResponseEntity<List<Ticket>> listAllTickets(){
        return ResponseEntity.ok(ticketService.fetchAllTickets());
    }

    @GetMapping("/getTicket/{id}")
    public ResponseEntity<Ticket> getTicket(@PathVariable String id){
        return ResponseEntity.ok(ticketService.getTicket(id));
    }

    @DeleteMapping("/deleteTicket/{id}")
    public ResponseEntity<DeleteResponse> deleteTicket(@PathVariable String id){
        DeleteResponse response = new DeleteResponse();
        if (ticketService.destroyTicket(id)){
            response.setMessage("Ticket deleted");
        }else {
            response.setMessage("Delete failed! Event does not exist");
        }
        return ResponseEntity.ok(response);
    }
}
