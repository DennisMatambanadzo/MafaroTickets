package online.epochsolutions.mafaro.services;

import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.contracts.IEventService;
import online.epochsolutions.mafaro.dtos.event.CreateEventRequest;
import online.epochsolutions.mafaro.dtos.event.UpdateEventRequest;
import online.epochsolutions.mafaro.models.Event;
import online.epochsolutions.mafaro.models.Host;
import online.epochsolutions.mafaro.repos.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EventService implements IEventService {

    private final EventRepository eventRepository;

    @Override
    public Event createEvent(CreateEventRequest request, Host user) {

        Event event = new Event();
        event.setDescription(request.getDescription());
        event.setName(request.getName());
        event.setVenue(request.getVenue());
        event.setEndTime(request.getEndTime());
        event.setStartTime(request.getStartTime());
        event.setAgeLimit(request.getAgeLimit());
        event.setSections(request.getSections());
        event.setCreatedBy(user);
       return eventRepository.save(event);
    }

    @Override
    public List<Event> fetchAllEvents(){
        return eventRepository.findAll();
    }

    @Override
    public Event getEvent(String id) {

        var opEvent = eventRepository.findById(id);
        return opEvent.orElseGet(Event::new);
    }

    @Override
    public Boolean deleteEvent(String id, Host user){
        eventRepository.deleteById(id);
        return eventRepository.findById(id).isEmpty();
    }

    @Override
    public Event updateEvent(UpdateEventRequest request, String id) {

        var opEvent = eventRepository.findById(id).get();

        if(Objects.nonNull(request.getName()) && !"".equalsIgnoreCase(request.getName())){
            opEvent.setName(request.getName());
        }

        if(Objects.nonNull(request.getStartTime())){
            opEvent.setStartTime(request.getStartTime());
        }

        if(Objects.nonNull(request.getEndTime())){
            opEvent.setEndTime(request.getEndTime());
        }

        if(Objects.nonNull(request.getAgeLimit()) ){
            opEvent.setAgeLimit(request.getAgeLimit());
        }

        if(Objects.nonNull(request.getLocation()) && !"".equalsIgnoreCase(request.getLocation())){
            opEvent.setVenue(request.getLocation());
        }


        return eventRepository.save(opEvent);
    }
}
