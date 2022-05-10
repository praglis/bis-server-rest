package rag.mil.bis;

import javax.jws.WebService;
import java.rmi.Remote;

@WebService
public interface EventClient extends Remote{
    GetEventsResponse getEvents();
    EventResponse createEvent(CreateEventRequest request);
    EventResponse getEvent(GetEventRequest request);
}