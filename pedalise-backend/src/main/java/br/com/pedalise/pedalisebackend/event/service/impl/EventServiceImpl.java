package br.com.pedalise.pedalisebackend.event.service.impl;

import br.com.pedalise.pedalisebackend.event.entity.Event;
import br.com.pedalise.pedalisebackend.event.exception.EventNotFoundException;
import br.com.pedalise.pedalisebackend.event.repository.EventRepository;
import br.com.pedalise.pedalisebackend.event.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public Event create(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event> findEventByStartDate(ZonedDateTime date) {
        return eventRepository.findByStartDate(date).orElse(null);
    }

    @Override
    public List<Event> findEventByEndDate(ZonedDateTime date) {
        return eventRepository.findByEndDate(date).orElse(null);
    }

    @Override
    public List<Event> findEventByTitle(String info) {
        return eventRepository.findByTitle(info).orElse(null);
    }

    @Override
    public List<Event> findEventByAuthorId(UUID id) {
        return eventRepository.findByAuthorId(id).orElse(null);
    }

    @Override
    public Event findEventById(UUID id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public Event deleteById(UUID id) {
        return eventRepository.findById(id).map(event -> {
            eventRepository.deleteById(id);
            return event;
        }).orElseThrow(() -> new EventNotFoundException("Evento não encontrado: " + id));
    }

    @Override
    public Event updateEvent(UUID id, Event editedEvent) {
        var actualEvent = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Evento não encontrado: " + id));

        actualEvent.setStartDate(editedEvent.getStartDate());
        actualEvent.setEndDate(editedEvent.getEndDate());
        actualEvent.setContent(editedEvent.getContent());
        actualEvent.setTitle(editedEvent.getTitle());
        actualEvent.setColor(editedEvent.getColor());

        return eventRepository.save(actualEvent);
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }
}
