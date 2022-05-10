package rag.mil.bis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


@WebService
@Service
@RequiredArgsConstructor
public class EventService {
    private static int id_seq = 1;

    private List<Event> events = new ArrayList<>();

    public List<Event> getEvents() {
        List<Event> events = new ArrayList<>();
        Event event = new Event();
        event.setName("Name");
        event.setType("Type");
        event.setDescription("Desc");
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date());
        XMLGregorianCalendar date2 = null;
        try {
            date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        event.setDate(date2);
        events.add(createEvent(event));
        Event event1 = new Event();
        event1.setName("Name");
        event1.setType("Type");
        event1.setDescription("Desc");
        event1.setDate(date2);
        events.add(createEvent(event1));
       return events;
    }

    public Event createEvent(Event event) {
        event.setId(id_seq++);
        events.add(event);
        return event;
    }

    public Event getEvent(long id) {
        Event event1 = new Event();
        event1.setName("Name");
        event1.setType("Type");
        event1.setDescription("Desc");
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date());
        XMLGregorianCalendar date2 = null;
        try {
            date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        event1.setDate(date2);
        return event1;
    }
}