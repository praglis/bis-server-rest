package rag.mil.bis;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rag.mil.bis.exception.EventNotFoundException;
import rag.mil.bis.exception.PdfGenerationException;
import rag.mil.bis.model.EventDto;
import rag.mil.bis.model.NewEventDto;
import rag.mil.bis.model.YearWeekDto;

import javax.jws.WebService;
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
    private final List<EventDto> events = new ArrayList<>();
    private int idSequence = 1;

    public List<EventDto> getEvents() {
        return events;
    }

    public EventDto createEvent(NewEventDto eventToCreate) {
        EventDto event = new EventDto();
        event.setId(idSequence++);
        event.setType(eventToCreate.getType());
        event.setName(eventToCreate.getName());
        event.setDescription(eventToCreate.getDescription());
        event.setDate(eventToCreate.getDate());
        events.add(event);
        return event;
    }

    public EventDto getEvent(long id) throws EventNotFoundException {
        EventDto dEvent = events.stream().filter(event -> event.getId() == id).findFirst().orElseThrow(EventNotFoundException::new);
        EventDto eventDto = new EventDto();
        eventDto.setDate(dEvent.getDate());
        eventDto.setId(dEvent.getId());
        eventDto.setDescription(dEvent.getDescription());
        eventDto.setType(dEvent.getType());
        eventDto.setName(dEvent.getName());
        return eventDto;
    }

    public List<EventDto> getEventsForDay(LocalDate day) {
        return events.stream().filter(event -> event.getDate().isEqual(day)).collect(Collectors.toList());
    }

    public List<EventDto> getEventsForWeek(YearWeekDto yearWeek) {
        return events.stream()
                .filter(event -> {
                    LocalDate date = event.getDate();

                    WeekFields weekFields = WeekFields.of(Locale.getDefault());
                    int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
                    return date.getYear() == yearWeek.getYear() && weekNumber == yearWeek.getWeek();
                }).collect(Collectors.toList());
    }

    public EventDto updateEvent(EventDto event) {
        long id = event.getId();
        EventDto uEvent = events.stream().filter(e -> e.getId() == id).findFirst().orElseThrow(EventNotFoundException::new);
        uEvent.setDate(event.getDate());
        uEvent.setDescription(event.getDescription());
        uEvent.setName(event.getName());
        uEvent.setType(event.getType());
        return event;
    }

    public void deleteEvent(long id) {
        events.removeIf(event -> event.getId() == id);
    }

    public byte[] generatePdf() {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, baos);

            document.open();

            PdfPTable table = new PdfPTable(5);
            addTableHeader(table);
            addRows(table);

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            throw new PdfGenerationException(e);
        }
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
        for (EventDto event : events) {
            table.addCell(event.getId() + "");
            table.addCell(event.getName());
            table.addCell(event.getType());
            table.addCell(event.getDate() + "");
            table.addCell(event.getDescription());
        }
    }

}
