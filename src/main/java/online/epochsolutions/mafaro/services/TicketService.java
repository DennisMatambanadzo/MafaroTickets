package online.epochsolutions.mafaro.services;

import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.dtos.CreateTicketRequest;
import online.epochsolutions.mafaro.models.Event;
import online.epochsolutions.mafaro.models.Ticket;
import online.epochsolutions.mafaro.repos.EventRepository;
import online.epochsolutions.mafaro.repos.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;


    public Integer generateTicket(CreateTicketRequest request){

        var opEvent = eventRepository.findById(request.getEventId()).get();

        Stream<Long> longStream = opEvent.getSections()
                .stream()
                .filter(section -> Objects.equals(section.getName(), request.getSection()))
                .filter(section -> section.getAvailableSlots() > 0).map((section) -> section.getAvailableSlots() - request.getNumberOfTickets());

        longStream.forEach(System.out::println);


        var ticketList = new ArrayList<Ticket>();



        for(int i = 0 ; i< request.getNumberOfTickets();i++) {
            ticketList.add(new Ticket());
        }

        ticketList.forEach(s-> s.setName(opEvent.getName()));
        ticketList.forEach(s-> s.setSection(opEvent.getName()));
        ticketList.forEach(s-> s.setStartTime(opEvent.getStartTime()));


        ticketRepository.saveAll(ticketList);

        return ticketList.size();
        
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
