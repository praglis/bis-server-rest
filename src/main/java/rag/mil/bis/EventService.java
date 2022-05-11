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
    private final List<Event> events = new ArrayList<>();
    private int idSequence = 1;

    public List<Event> getEvents() {
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
        event.setId(idSequence++);
        events.add(event);
        return event;
    }

    public Event getEvent(long id) {
        return events.stream().filter(event -> event.getId() == id).findFirst().orElse(null);
    }
}
