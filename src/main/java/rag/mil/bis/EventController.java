package rag.mil.bis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.jws.WebService;


@Controller
@WebService(endpointInterface = "rag.mil.bis.EventClient")
@RequiredArgsConstructor
public class EventController implements EventClient {
    @Autowired
    private EventService eventService;

    public GetEventsResponse getEvents() {
        GetEventsResponse response = new GetEventsResponse();
        response.getEvent().addAll(eventService.getEvents());
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
    public DetailedEventResponse getEvent(GetEventRequest request) {
        DetailedEventResponse response = new DetailedEventResponse();
        DetailedEvent event = eventService.getEvent(request.getId());
        response.setEvent(event);
        return response;
    }

    @Override
    public GetEventsResponse getEventsForDay(GetEventsForDayRequest request) {
        GetEventsResponse response = new GetEventsResponse();
        response.event = eventService.getEventsForDay(request.getDay());
        return response;
    }

    @Override
    public GetEventsResponse getEventsForWeek(GetEventsForWeekRequest request) {
        GetEventsResponse response = new GetEventsResponse();
        response.event = eventService.getEventsForWeek(request.getWeek());
        return response;
    }
}
