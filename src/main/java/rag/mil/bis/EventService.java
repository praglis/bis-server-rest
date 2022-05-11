package rag.mil.bis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;


@WebService
@Service
@RequiredArgsConstructor
public class EventService {
    private final List<Event> events = new ArrayList<>();
    private int idSequence = 1;

    public List<Event> getEvents() {
       return events;
    }

    public Event createEvent(Event event) {
        event.setId(idSequence++);
        event.getDate().setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        events.add(event);
        return event;
    }

    public DetailedEvent getEvent(long id) {
        Event dEvent = events.stream().filter(event -> event.getId() == id).findFirst().orElse(null);
        if (dEvent == null) {
            return null;
        }
        DetailedEvent detailedEvent = new DetailedEvent();
        detailedEvent.setDate(dEvent.getDate());
        detailedEvent.setId(dEvent.getId());
        detailedEvent.setDescription(dEvent.getDescription());
        detailedEvent.setType(dEvent.getType());
        detailedEvent.setName(dEvent.getName());
        XMLGregorianCalendar date = dEvent.getDate();
        LocalDate localDate = LocalDate.of(
                date.getYear(),
                date.getMonth(),
                date.getDay());
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        short weekNumber = (short)localDate.get(weekFields.weekOfWeekBasedYear());
        detailedEvent.setWeek(weekNumber);
        detailedEvent.setYear(dEvent.getDate().getYear());
        detailedEvent.setMonth((short)dEvent.getDate().getMonth());
        return detailedEvent;
    }

    public List<Event> getEventsForDay(XMLGregorianCalendar day) {
        return events.stream().filter(event -> event.getDate().equals(day)).collect(Collectors.toList());
    }

    public List<Event> getEventsForWeek(short week) {
        return events.stream().filter(event -> {
            XMLGregorianCalendar date = event.getDate();
            LocalDate localDate = LocalDate.of(
                    date.getYear(),
                    date.getMonth(),
                    date.getDay());
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int weekNumber = localDate.get(weekFields.weekOfWeekBasedYear());
            return weekNumber == week;
        }).collect(Collectors.toList());
    }
}
