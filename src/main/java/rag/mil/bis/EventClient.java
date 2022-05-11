package rag.mil.bis;

import javax.jws.WebService;
import java.rmi.Remote;

@WebService
public interface EventClient extends Remote {
    GetEventsResponse getEvents();

    EventResponse createEvent(CreateEventRequest request);
    DetailedEventResponse getEvent(GetEventRequest request);
    GetEventsResponse getEventsForDay(GetEventsForDayRequest request);
    GetEventsResponse getEventsForWeek(GetEventsForWeekRequest request);
}
