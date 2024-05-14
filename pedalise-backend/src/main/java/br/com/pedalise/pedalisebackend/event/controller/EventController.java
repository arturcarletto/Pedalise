package br.com.pedalise.pedalisebackend.event.controller;

import br.com.pedalise.pedalisebackend.event.entity.Event;
import br.com.pedalise.pedalisebackend.event.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
@AllArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        return ResponseEntity.ok(eventService.create(event));
    }

    @GetMapping("/")
    public ResponseEntity<List<Event>> findAll() {
        return ResponseEntity.ok(eventService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> findEventById(@PathVariable UUID id) {
        return ResponseEntity.ok(eventService.findEventById(id));
    }

    @GetMapping("/postDate/{postDate}")
    public ResponseEntity<List<Event>> findEventByPostDate(@PathVariable Date postDate) {
        return ResponseEntity.ok(eventService.findEventByPostDate(postDate));
    }

    @GetMapping("/date/{eventDate}")
    public ResponseEntity<List<Event>> findEventByEventDate(@PathVariable Date eventDate) {
        return ResponseEntity.ok(eventService.findEventByEventDate(eventDate));
    }

    @GetMapping("/content/{title}")
    public ResponseEntity<List<Event>> findEventByTitle(@PathVariable String title) {
        return ResponseEntity.ok(eventService.findEventByTitle(title));
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<List<Event>> findEventByAuthorId(@PathVariable UUID id) {
        return ResponseEntity.ok(eventService.findEventByAuthorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable UUID id, @RequestBody Event event) {
        return ResponseEntity.ok(eventService.updateEvent(id, event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable UUID id) {
        return ResponseEntity.ok(eventService.deleteById(id));
    }
}