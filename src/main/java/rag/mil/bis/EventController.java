package rag.mil.bis;

import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rag.mil.bis.exceptions.IdInconsistencyException;
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
@Validated
public class EventController {
    private static final String ID_SHOULD_NOT_BE_NEGATIVE_MSG = "Id can't be negative number.";
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
    public EventDto getEvent(@Range(message = ID_SHOULD_NOT_BE_NEGATIVE_MSG) @PathVariable long id) {
        return eventService.getEvent(id);
    }

    @GetMapping("/day/{day}")
    public List<EventDto> getEventsForDay(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
        return eventService.getEventsForDay(day);
    }

    @GetMapping("/year/{year}/week/{week}")
    public List<EventDto> getEventsForWeek(@PathVariable short year, @PathVariable short week) {
        return eventService.getEventsForWeek(new YearWeekDto(week, year));
    }

    @PutMapping("/{id}")
    public EventDto updateEvent(@Range(message = ID_SHOULD_NOT_BE_NEGATIVE_MSG) @PathVariable long id,
                                @Valid @RequestBody EventDto event) {
        if (id != event.getId())
            throw new IdInconsistencyException(id, event.getId());

        return eventService.updateEvent(event);
    }

    @DeleteMapping("/{id}")
    public EventDto deleteEvent(@Range(message = ID_SHOULD_NOT_BE_NEGATIVE_MSG) @PathVariable long id) {
        return eventService.deleteEvent(id);
    }

    @GetMapping("/pdf")
    public byte[] generatePdf() {
        return eventService.generatePdf();
    }
}
