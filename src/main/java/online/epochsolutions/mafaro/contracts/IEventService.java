package online.epochsolutions.mafaro.contracts;

import online.epochsolutions.mafaro.dtos.event.CreateEventRequest;
import online.epochsolutions.mafaro.dtos.event.UpdateEventRequest;
import online.epochsolutions.mafaro.models.Event;
import online.epochsolutions.mafaro.models.Organiser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IEventService {
    Event createEvent(CreateEventRequest request, Organiser user);

    List<Event> fetchAllEvents();

    Event getEvent(String id);

    Boolean deleteEvent(String id, Organiser user);

    Event updateEvent(UpdateEventRequest request, String id);
}
