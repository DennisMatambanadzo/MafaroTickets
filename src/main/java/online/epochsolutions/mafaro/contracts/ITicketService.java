package online.epochsolutions.mafaro.contracts;

import online.epochsolutions.mafaro.dtos.ticket.CreateTicketsRequest;
import online.epochsolutions.mafaro.exceptions.EmailFailureException;
import online.epochsolutions.mafaro.models.Patron;
import online.epochsolutions.mafaro.models.Ticket;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ITicketService {
    @Transactional
    List<Ticket> generateTicket(CreateTicketsRequest request, Patron patron) throws EmailFailureException;

    List<Ticket> fetchAllTickets();

    Ticket getTicket(String id);

    boolean destroyTicket(String id);

    void checkEventAttributes();
}
