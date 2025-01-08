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