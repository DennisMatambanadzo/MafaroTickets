package online.epochsolutions.mafaro.services;

import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.contracts.ITicketService;
import online.epochsolutions.mafaro.dtos.ticket.CreateTicketsRequest;
import online.epochsolutions.mafaro.exceptions.EmailFailureException;
import online.epochsolutions.mafaro.models.Patron;
import online.epochsolutions.mafaro.models.Ticket;
import online.epochsolutions.mafaro.repos.EventRepository;
import online.epochsolutions.mafaro.repos.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;


@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final TicketPurchaseNotificationEmailService emailService;
    private final TicketJWTService ticketJWTService;

    @Override
    @Transactional
    public List<Ticket> generateTicket(CreateTicketsRequest request, Patron patron) throws EmailFailureException {

        var opEvent = eventRepository.findById(request.getEventId());

       if (opEvent.isPresent() && patron.getClass()== Patron.class){
           var event = opEvent.get();
           OptionalLong max = event.getSections()
                   .stream()
                   .filter(section -> Objects.equals(section.getName(), request.getSection()))
                   .filter(section -> section.getAvailableTicketSlots() > 0)
                   .mapToLong((section) -> section.getAvailableTicketSlots() - request.getNumberOfTickets())
                   .max();

           if (max.isPresent() && max.getAsLong()>=0 ){
               event.getSections()
                       .stream()
                       .filter(section -> Objects.equals(section.getName(), request.getSection()))
                       .forEach(section -> section.setAvailableTicketSlots(max.getAsLong()));

               eventRepository.save(event);
               var ticketList = new ArrayList<Ticket>();


               for(int i = 0; i< request.getNumberOfTickets(); i++) {
                   ticketList.add(new Ticket());
               }

               ticketList.forEach(s-> s.setName(event.getName()));
               ticketList.forEach(s-> s.setSection(request.getSection()));
               ticketList.forEach(s-> s.setStartTime(event.getStartTime()));
               ticketList.forEach(s-> s.setPurchasedBy(patron.getEmail()));
               ticketList.forEach(s-> s.setTicketToken(ticketJWTService.generateTicketToken(s)));
               ticketList.forEach(s-> s.setCreatedAt(new Timestamp(System.currentTimeMillis())));

                ticketRepository.saveAll(ticketList);
                emailService.sendTicketPurchaseEmail(ticketList,patron.getEmail());
                return ticketList;
           }
       }
        return new ArrayList<>();
    }


    @Override
    public List<Ticket> fetchAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket getTicket(String id) {

        var opTicket = ticketRepository.findById(id);
        return opTicket.orElseGet(Ticket::new);
    }

    @Override
    public boolean destroyTicket(String id) {
        ticketRepository.deleteById(id);
        return ticketRepository.findById(id).isEmpty();
    }

    @Override
    public void checkEventAttributes(){

    }
}
