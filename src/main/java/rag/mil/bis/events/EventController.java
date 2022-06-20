package rag.mil.bis.events;

import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rag.mil.bis.exception.EventNotFoundException;
import rag.mil.bis.exception.IdInconsistencyException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    @GetMapping
    public List<Event> getEvents() {
        return eventService.getEvents();
    }

    @PostMapping
    public Event createEvent(NewEventDto newEventDto) {
        return eventService.createEvent(newEventDto);
    }

    @GetMapping("/{id}")
    public DetailedEvent getEvent(@RequestParam long id) throws EventNotFoundException {
        return eventService.getEvent(id);
    }

    @GetMapping("/day/{day}")
    public List<Event> getEventsForDay(@PathVariable LocalDate day) {
        return eventService.getEventsForDay(day);
    }

    @GetMapping("/year/{yearWeekDto}")
    public List<Event> getEventsForWeek(@PathVariable YearWeek yearWeekDto) {
        return eventService.getEventsForWeek(yearWeekDto);
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable long id, @Valid Event event) {
        if (id != event.getId())
            throw new IdInconsistencyException(id, event.getId());

        return eventService.updateEvent(event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable long id) {
        eventService.deleteEvent(id);
    }

    @GetMapping("/pdf")
    public byte[] generatePdf() throws DocumentException {
        return eventService.generatePdf();
    }
}
