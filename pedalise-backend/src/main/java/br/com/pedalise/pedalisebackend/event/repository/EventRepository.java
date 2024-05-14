package br.com.pedalise.pedalisebackend.event.repository;

import br.com.pedalise.pedalisebackend.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    Optional<List<Event>> findByTitle(String info);

    Optional<List<Event>> findByAuthorId(UUID authorId);

    Optional<List<Event>> findByPostDate(Date postDate);

    Optional<List<Event>> findByEventDate(Date eventDate);

}