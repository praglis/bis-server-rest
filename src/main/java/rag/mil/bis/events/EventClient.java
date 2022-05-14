package rag.mil.bis.events;

import com.itextpdf.text.DocumentException;
import rag.mil.bis.exception.EmptyDataException;
import rag.mil.bis.exception.EventNotFoundException;

import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import java.rmi.Remote;
import java.util.List;

@WebService
public interface EventClient extends Remote {
    List<Event> getEvents();

    Event createEvent(EventToCreate event);

    DetailedEvent getEvent(long id) throws EventNotFoundException;

    List<Event> getEventsForDay(XMLGregorianCalendar day);

    List<Event> getEventsForWeek(YearWeek yearWeekDto);

    Event updateEvent(Event event) throws EventNotFoundException, EmptyDataException;

    void deleteEvent(long id);

    byte[] generatePdf() throws DocumentException;
}

