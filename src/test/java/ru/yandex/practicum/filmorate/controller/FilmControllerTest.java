package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    private final FilmController filmController = new FilmController();


    @Test
    void filmCreateTest() {
        Film filmCreate = Film.builder()
                .name("Film Name")
                .description("Film Description")
                .releaseDate(LocalDate.of(1967, 3, 25))
                .duration(100L)
                .build();
        assertEquals("Film Name", filmController.addFilm(filmCreate).getName());
    }

    @Test
    void filmCreateFailNameTest() {
        Film filmCreateFailName = Film.builder()
                .name("")
                .description("Film Description")
                .releaseDate(LocalDate.of(1900, 3, 25))
                .duration(100L)
                .build();
        assertThrows(ValidationException.class, () -> filmController.addFilm(filmCreateFailName));
    }

    @Test
    void filmCreateFailDescriptionTest() {
        Film filmCreateFailDescription = Film.builder()
                .name("Film Name")
                .description("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. " +
                        "Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, " +
                        "а именно 20 миллионов. о Куглов, который за время «своего отсутствия», " +
                        "стал кандидатом Коломбани.")
                .releaseDate(LocalDate.of(1900, 3, 25))
                .duration(200L)
                .build();
        assertThrows(ValidationException.class,
                () -> filmController.addFilm(filmCreateFailDescription));
    }

    @Test
    void filmCreateFailReleaseDateTest() {
        Film filmCreateFailReleaseDate = Film.builder()
                .name("Film Name")
                .description("Film Description")
                .releaseDate(LocalDate.of(1890, 3, 25))
                .duration(100L)
                .build();
        assertThrows(ValidationException.class,
                () -> filmController.addFilm(filmCreateFailReleaseDate));
    }

    @Test
    void filmCreateFailDurationTest() {
        Film filmCreateFailDuration = Film.builder()
                .name("Film Name")
                .description("Film Description")
                .releaseDate(LocalDate.of(1980, 3, 25))
                .duration(-200L)
                .build();
        assertThrows(ValidationException.class,
                () -> filmController.addFilm(filmCreateFailDuration));
    }

    @Test
    void filmUpdateTest() {
        Film filmCreate = Film.builder()
                .name("Film Name")
                .description("Film Description")
                .releaseDate(LocalDate.of(1967, 3, 25))
                .duration(100L)
                .build();
        Film filmUpdate = filmController.addFilm(filmCreate);
        filmUpdate.setName("Film Update");
        assertEquals("Film Update", filmController.updateFilm(filmUpdate).getName());
    }

    @Test
    void filmUpdateUnknownTest() {
        Film filmUpdateUnknown = Film.builder()
                .id(999L)
                .name("Film Name")
                .description("Film Description")
                .releaseDate(LocalDate.of(1967, 3, 25))
                .duration(100L)
                .build();
        assertThrows(ValidationException.class, () -> filmController.updateFilm(filmUpdateUnknown));
    }

    @Test
    void filmGetAllTest() {
        Collection<Film> filmGetAll = filmController.getAllFilms();
        assertNotNull(filmGetAll);
    }
}