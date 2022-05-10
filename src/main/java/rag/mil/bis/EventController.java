package rag.mil.bis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.jws.WebService;


@Controller
@WebService(endpointInterface = "rag.mil.bis.EventClient")
@RequiredArgsConstructor
public class EventController implements EventClient {
    @Autowired
    private EventService eventService;

    public GetEventsResponse getEvents() {
        GetEventsResponse response = new GetEventsResponse();
        response.event = eventService.getEvents();
        return response;
    }

    @Override
    public EventResponse createEvent(CreateEventRequest request) {
        EventResponse response = new EventResponse();
        Event event = eventService.createEvent(request.getEvent());
        response.setEvent(event);
        return response;
    }

    @Override
    public EventResponse getEvent(GetEventRequest request) {
        EventResponse response = new EventResponse();
        Event event = eventService.getEvent(request.getId());
        response.setEvent(event);
        return response;
    }
}
