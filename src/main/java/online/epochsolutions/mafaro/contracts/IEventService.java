package online.epochsolutions.mafaro.contracts;

import online.epochsolutions.mafaro.dtos.event.CreateEventRequest;
import online.epochsolutions.mafaro.dtos.event.UpdateEventRequest;
import online.epochsolutions.mafaro.models.Event;
import online.epochsolutions.mafaro.models.Host;

import java.util.List;

public interface IEventService {
    Event createEvent(CreateEventRequest request, Host user);

    List<Event> fetchAllEvents();

    Event getEvent(String id);

    Boolean deleteEvent(String id, Host user);

    Event updateEvent(UpdateEventRequest request, String id);
}
