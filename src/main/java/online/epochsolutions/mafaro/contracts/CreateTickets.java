package online.epochsolutions.mafaro.contracts;

import online.epochsolutions.mafaro.dtos.CreateTicketRequest;
import online.epochsolutions.mafaro.models.Event;
import online.epochsolutions.mafaro.models.Ticket;

import java.util.ArrayList;

@FunctionalInterface
public interface CreateTickets {

    ArrayList<Ticket> generateTickets(CreateTicketRequest request, Event event);
}
