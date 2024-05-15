package br.com.pedalise.pedalisebackend.event.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Entity(name = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private String title;

    private String content;

    private UUID authorId;

    private String color;


}
