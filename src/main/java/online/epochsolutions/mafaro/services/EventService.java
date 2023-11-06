package online.epochsolutions.mafaro.services;

import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.dtos.CreateEventRequest;
import online.epochsolutions.mafaro.dtos.UpdateEventRequest;
import online.epochsolutions.mafaro.models.Event;
import online.epochsolutions.mafaro.repos.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Event createEvent(CreateEventRequest request) {

        Event event = new Event();
        event.setEventDescription(request.getEventDescription());
        event.setName(request.getName());
        event.setLocation(request.getLocation());
        event.setEndTime(request.getEndTime());
        event.setStartTime(request.getStartTime());
        event.setAgeLimit(request.getAgeLimit());
       return eventRepository.save(event);
    }

    public List<Event> fetchAllEvents(){
        return eventRepository.findAll();
    }

    public Event getEvent(String id) {

        var opEvent = eventRepository.findById(id);
        return opEvent.orElseGet(Event::new);
    }

    public Boolean deleteEvent(String id){
        eventRepository.deleteById(id);
        return eventRepository.findById(id).isEmpty();
    }

    public Event updateEvent(UpdateEventRequest request,String id) {

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
            opEvent.setLocation(request.getLocation());
        }


        return eventRepository.save(opEvent);
    }
}
