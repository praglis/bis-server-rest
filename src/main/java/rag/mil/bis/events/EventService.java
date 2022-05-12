package rag.mil.bis.events;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rag.mil.bis.exception.EmptyDataException;
import rag.mil.bis.exception.EventNotFoundException;

import javax.jws.WebService;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@WebService
@Service
@RequiredArgsConstructor
public class EventService {
    private final List<Event> events = new ArrayList<>();
    private int idSequence = 1;

    public List<Event> getEvents() {
       return events;
    }

    public Event createEvent(EventToCreate eventToCreate) {
        Event event = new Event();
        event.setId(idSequence++);
        eventToCreate.getDate().setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        event.setType(eventToCreate.getType());
        event.setName(eventToCreate.getName());
        event.setDescription(eventToCreate.getDescription());
        event.setDate(eventToCreate.getDate());
        events.add(event);
        return event;
    }

    public DetailedEvent getEvent(long id) throws EventNotFoundException {
        Event dEvent = events.stream().filter(event -> event.getId() == id).findFirst().orElseThrow(EventNotFoundException::new);
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

    public Event updateEvent(Event event) throws EmptyDataException, EventNotFoundException {
        long id = event.getId();
        if(id == 0) {
            throw new EmptyDataException("Id");
        }
        Event uEvent = events.stream().filter(e -> e.getId() == id).findFirst().orElseThrow(EventNotFoundException::new);
        uEvent.setDate(event.getDate());
        uEvent.setDescription(event.getDescription());
        uEvent.setName(event.getDescription());
        uEvent.setType(event.getType());
        return event;
    }

    public void deleteEvent(long id) {
        events.removeIf(event -> event.getId() == id);
    }

    public byte[] generatePdf() throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();

        PdfPTable table = new PdfPTable(5);
        addTableHeader(table);
        addRows(table);

        document.add(table);
        document.close();
        return baos.toByteArray();
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Id", "Name", "Type", "Date", "Description")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table) {
        for(Event event: events) {
            table.addCell(event.getId()+ "");
            table.addCell(event.getName());
            table.addCell(event.getType());
            table.addCell(event.getDate() + "");
            table.addCell(event.getDescription());
        }
    }
}

