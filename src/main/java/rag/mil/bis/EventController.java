package rag.mil.bis;

import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Controller;
import rag.mil.bis.exception.EmptyDataException;
import rag.mil.bis.exception.EventNotFoundException;

import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.soap.MTOM;
import java.io.*;
import java.util.List;


@Controller
@WebService(endpointInterface = "rag.mil.bis.EventClient")
@RequiredArgsConstructor
public class EventController implements EventClient {
    @Autowired
    private EventService eventService;

    public List<Event> getEvents() {
        return eventService.getEvents();
    }

    @Override
    public Event createEvent(EventToCreate event) {
        return eventService.createEvent(event);
    }

    @Override
    public DetailedEvent getEvent(long id) throws EventNotFoundException {
        return eventService.getEvent(id);
    }

    @Override
    public List<Event> getEventsForDay(XMLGregorianCalendar day) {
        return eventService.getEventsForDay(day);
    }

    @Override
    public List<Event> getEventsForWeek(short week) {
        return eventService.getEventsForWeek(week);
    }

    @Override
    public Event updateEvent(Event event) throws EventNotFoundException, EmptyDataException {
        return eventService.updateEvent(event);
    }

    @Override
    public void deleteEvent(long id) {
        eventService.deleteEvent(id);
    }

    @Override
    @MTOM
    public void generatePdf() throws DocumentException {
        eventService.generatePdf();
    }
}
