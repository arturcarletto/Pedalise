package br.com.pedalise.pedalisebackend;

import br.com.pedalise.pedalisebackend.event.entity.Event;
import br.com.pedalise.pedalisebackend.event.service.EventService;
import br.com.pedalise.pedalisebackend.user.entity.User;
import br.com.pedalise.pedalisebackend.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class PedaliseBackendApplicationTests {


    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;

    private User gustavo,augusto,artur;
    private Event event1,event2,event3;

    private final ZonedDateTime datetime = ZonedDateTime.of(2024,5,14,0,0,0,0, ZoneOffset.UTC);


    @BeforeEach
    void setup(){
        gustavo = userService.create(new User(UUID.randomUUID(),"Gustavo","pipoca1","Gutokamradt@gmail.com"));
        augusto = userService.create(new User(UUID.randomUUID(),"Augusto","pipoca2","Augusto.contato@gmail.com"));
        artur = userService.create(new User(UUID.randomUUID(),"Artur","pipoca3","ArturCarletto@gmail.com"));

        event1 = eventService.create(new Event(UUID.randomUUID(),datetime,datetime,"Campeonato de arremesso de anao","titulo auto explicativo",gustavo.getId()));
        event2 = eventService.create(new Event(UUID.randomUUID(),datetime,datetime,"Rodizio no restaurante Dom Zelittus","titulo auto explicativo",artur.getId()));
        event3 = eventService.create(new Event(UUID.randomUUID(),datetime,datetime,"Recrutamento do Exercicito privado","titulo auto explicativo",augusto.getId()));
    }

    @Test
    void findUserByIdTest(){
        var user1 = userService.getUserById(gustavo.getId());

        assertThat(user1).isEqualTo(gustavo);
    }

    @Test
    void findUserByUsernameTest(){

        var user1 = userService.getUserByUsername("Gustavo");

        assertThat(user1).isEqualTo(gustavo);
    }

    @Test
    void findUserByEmailTest(){

        var user1 = userService.getUserByEmail("Gutokamradt@gmail.com");

        assertThat(user1).isEqualTo(gustavo);
    }

    @Test
    void findEventByIdTest(){

        var event = eventService.findEventById(event1.getId());

        assertThat(event).isEqualTo(event1);
    }

    @Test
    void findEventByEventDate(){

        var event = eventService.findEventByEventDate(datetime);

        assertThat(event.getFirst().getEventDate()).isEqualTo(datetime);
    }

    @Test
    void findEventByPostDate(){

        var event = eventService.findEventByPostDate(datetime);

        for (int i = 0; i < event.size(); i++) {
            assertThat(event.get(i).getEventDate()).isEqualTo(datetime);
        }

    }

    @Test
    void findEventByAuthorIdTest(){
        var event = eventService.findEventByAuthorId(gustavo.getId());

        for (int i = 0; i < event.size(); i++) {
            assertThat(event.get(i).getAuthorId()).isEqualTo(gustavo.getId());
        }

    }

    @Test
    void findEventByTitle(){

        var testEvent1 = eventService.findEventByTitle("Campeonato de arremesso de anao");
        var testEvent2 = eventService.findEventByTitle("Rodizio no restaurante Dom Zelittus");
        var testEvent3 = eventService.findEventByTitle("Recrutamento do Exercicito privado");

        assertThat(testEvent1.getFirst()).isEqualTo(event1);
        assertThat(testEvent2.getFirst()).isEqualTo(event2);
        assertThat(testEvent3.getFirst()).isEqualTo(event3);
    }
}
