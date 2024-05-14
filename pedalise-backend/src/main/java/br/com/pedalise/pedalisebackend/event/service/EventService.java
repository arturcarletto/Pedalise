package br.com.pedalise.pedalisebackend.event.service;

import br.com.pedalise.pedalisebackend.event.entity.Event;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface EventService {

    Event create(@NotNull Event event);

    List<Event> findEventByPostDate(@NotNull ZonedDateTime date);

    List<Event> findEventByEventDate(@NotNull ZonedDateTime date);

    List<Event> findEventByTitle(@NotNull String title);

    List<Event> findEventByAuthorId(@NotNull UUID id);

    Event findEventById(@NotNull UUID id);

    Event deleteById(@NotNull UUID id);

    Event updateEvent(UUID id, @NotNull Event event);

    List<Event> findAll();

}
