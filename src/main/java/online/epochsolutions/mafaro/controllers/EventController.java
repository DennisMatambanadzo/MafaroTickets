package online.epochsolutions.mafaro.controllers;

import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.dtos.event.CreateEventRequest;
import online.epochsolutions.mafaro.dtos.common.DeleteResponse;
import online.epochsolutions.mafaro.dtos.event.UpdateEventRequest;
import online.epochsolutions.mafaro.models.Event;
import online.epochsolutions.mafaro.models.Host;
import online.epochsolutions.mafaro.contracts.IEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mafaro/admin/events")
public class EventController {

    private final IEventService IEventService;

    @PostMapping("/createEvent")
    public ResponseEntity<Event> createEvent(@RequestBody CreateEventRequest request, @AuthenticationPrincipal Host user){

        return ResponseEntity.ok(IEventService.createEvent(request,user));
    }

    @GetMapping("/listEvents")
    public ResponseEntity<List<Event>> fetchAllEvents(){
        return ResponseEntity.ok(IEventService.fetchAllEvents());
    }

    @GetMapping("/getEvent/{id}")
    public ResponseEntity<Event> getEvent( @PathVariable String id){

        if (IEventService.getEvent(id) != null){
            return ResponseEntity.ok(IEventService.getEvent(id));
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteEvent/{id}")
    public ResponseEntity<DeleteResponse> deleteEvent( @PathVariable String id, @AuthenticationPrincipal Host user){
        DeleteResponse response = new DeleteResponse();

        if (IEventService.deleteEvent(id,user)){
            response.setMessage("Event deleted");
        }else {
            response.setMessage("Delete failed! Event does not exist");
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateEvent/{id}")
    public ResponseEntity<Event> updateEvent(@RequestBody UpdateEventRequest request, @PathVariable String id){
        return ResponseEntity.ok(IEventService.updateEvent(request,id));
    }

}
