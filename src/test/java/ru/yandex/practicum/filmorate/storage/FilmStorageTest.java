package ru.yandex.practicum.filmorate.storage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmStorageTest {
    @Autowired
    private FilmStorage filmStorage;

    @Test
    void filmCreateTest() {
        Film filmCreate = Film.builder()
                .name("Film Name")
                .description("Film Description")
                .releaseDate(LocalDate.of(1967, 3, 25))
                .duration(100L)
                .build();
        assertEquals("Film Name", filmStorage.addFilm(filmCreate).getName());
    }

    @Test
    void filmUpdateTest() {
        Film filmCreate = Film.builder()
                .name("Film Name")
                .description("Film Description")
                .releaseDate(LocalDate.of(1967, 3, 25))
                .duration(100L)
                .build();
        Film filmUpdate = filmStorage.addFilm(filmCreate);
        filmUpdate.setName("Film Update");
        assertEquals("Film Update", filmStorage.updateFilm(filmUpdate).getName());
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
        assertThrows(NotFoundException.class, () -> filmStorage.updateFilm(filmUpdateUnknown));
    }

    @Test
    void filmGetAllTest() {
        Film film = Film.builder()
                .name("Film Name")
                .description("Film Description")
                .releaseDate(LocalDate.of(1967, 3, 25))
                .duration(100L)
                .build();
        Film filmCreated = filmStorage.addFilm(film);

        List<Film> filmGetAll = filmStorage.getAllFilms();
        assertTrue(filmGetAll.contains(filmCreated));
    }

    @Test
    void filmGetByIdTest() {
        Film film = Film.builder()
                .name("Film Name")
                .description("Film Description")
                .releaseDate(LocalDate.of(1967, 3, 25))
                .duration(100L)
                .build();
        Film filmCreated = filmStorage.addFilm(film);
        assertEquals("Film Name", filmStorage.getFilmById(filmCreated.getId()).getName());
    }

    @Test
    void filmGetFilmByIdUnknownTest() {
        assertThrows(NotFoundException.class, () -> filmStorage.getFilmById(999L));
    }
}