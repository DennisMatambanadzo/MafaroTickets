package online.epochsolutions.mafaro.services;

import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.dtos.CreateTicketRequest;
import online.epochsolutions.mafaro.models.Event;
import online.epochsolutions.mafaro.models.Ticket;
import online.epochsolutions.mafaro.repos.EventRepository;
import online.epochsolutions.mafaro.repos.TicketRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalLong;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;


    public Boolean generateTicket(CreateTicketRequest request){

        var opEvent = eventRepository.findById(request.getEventId());

       if (opEvent.isPresent()){
           var event = opEvent.get();
           OptionalLong max = event.getSections()
                   .stream()
                   .filter(section -> Objects.equals(section.getName(), request.getSection()))
                   .filter(section -> section.getAvailableSlots() > 0)
                   .mapToLong((section) -> section.getAvailableSlots() - request.getNumberOfTickets())
                   .max();

           if (max.isPresent() && max.getAsLong()>=0 ){
               event.getSections()
                       .stream()
                       .filter(section -> Objects.equals(section.getName(), request.getSection()))
                       .forEach(section -> section.setAvailableSlots(max.getAsLong()));

               eventRepository.save(event);
               var ticketList = new ArrayList<Ticket>();


               for(int i = 0; i< request.getNumberOfTickets(); i++) {
                   ticketList.add(new Ticket());
               }

               ticketList.forEach(s-> s.setName(event.getName()));
               ticketList.forEach(s-> s.setSection(request.getSection()));
               ticketList.forEach(s-> s.setStartTime(event.getStartTime()));
               ticketList.forEach(s -> s.setCreatedAt(new Timestamp(System.currentTimeMillis())));

               ticketRepository.saveAll(ticketList);

               return true;
           }

       }

        return false;

    }




    public List<Ticket> fetchAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicket(String id) {

        var opTicket = ticketRepository.findById(id);
        return opTicket.orElseGet(Ticket::new);
    }

    public boolean destroyTicket(String id) {
        ticketRepository.deleteById(id);
        return ticketRepository.findById(id).isEmpty();
    }

    public void checkEventAttributes(){

    }
}
