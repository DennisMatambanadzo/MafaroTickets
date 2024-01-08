package online.epochsolutions.mafaro.services;

import online.epochsolutions.mafaro.dtos.event.CreateEventRequest;
import online.epochsolutions.mafaro.models.Event;
import online.epochsolutions.mafaro.models.Organiser;
import online.epochsolutions.mafaro.repos.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    EventRepository eventRepository;

    @InjectMocks
    EventService eventService;

    @Test
    void EventService_CreateEvent(){

        var event = new Event();
        event.setName("Event1");


        var eventRequest= new CreateEventRequest();
        eventRequest.setName("Event1");
        
        var organiser = new Organiser();
        organiser.setEmail("organiser");

        when(eventRepository.save(Mockito.any(Event.class))).thenReturn(event);

        Event event1 = eventService.createEvent(eventRequest, organiser);
        assertThat(event1).isNotNull();
    }

    @Test
    void EventService_FetchAllEvents_ReturnEvents(){

        var event1 = new Event();
        event1.setName("Event1");
        var event2 = new Event();
        event2.setName("Event1");

        var eventList = new ArrayList<Event>();
        eventList.add(event1);
        eventList.add(event2);

        when(eventRepository.findAll()).thenReturn(eventList);

        List<Event> events = eventService.fetchAllEvents();

        assertThat(events).isNotEmpty();
        assertThat(events.size()).isEqualTo(2);

    }

    @Test
    void EventService_getEvent_ReturnEvent(){
        var event1 = new Event();
        String id = "12345";
        event1.setId(id);
        event1.setName("Event1");

        when(eventRepository.findById(id)).thenReturn(Optional.of(event1));

        Event event = eventService.getEvent(id);

        assertThat(event.getName()).isEqualTo("Event1");

    }


}