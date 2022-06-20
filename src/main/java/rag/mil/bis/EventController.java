package rag.mil.bis;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rag.mil.bis.exception.IdInconsistencyException;
import rag.mil.bis.model.EventDto;
import rag.mil.bis.model.NewEventDto;
import rag.mil.bis.model.YearWeekDto;

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
    public List<EventDto> getEvents() {
        return eventService.getEvents();
    }

    @PostMapping
    public EventDto createEvent(@Valid @RequestBody NewEventDto newEventDto) {
        return eventService.createEvent(newEventDto);
    }

    @GetMapping("/{id}")
    public EventDto getEvent(@PathVariable long id) {
        return eventService.getEvent(id);
    }

    @GetMapping("/day/{day}")
    public List<EventDto> getEventsForDay(@PathVariable LocalDate day) {
        return eventService.getEventsForDay(day);
    }

    @GetMapping("/year/{year}/week/{week}")
    public List<EventDto> getEventsForWeek(@PathVariable short year, @PathVariable short week) {
        return eventService.getEventsForWeek(new YearWeekDto(week, year));
    }

    @PutMapping("/{id}")
    public EventDto updateEvent(@PathVariable long id, @Valid @RequestBody EventDto event) {
        if (id != event.getId())
            throw new IdInconsistencyException(id, event.getId());

        return eventService.updateEvent(event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable long id) {
        eventService.deleteEvent(id);
    }

    @GetMapping("/pdf")
    public byte[] generatePdf() {
        return eventService.generatePdf();
    }
}
