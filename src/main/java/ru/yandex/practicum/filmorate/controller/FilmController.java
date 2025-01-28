package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private Long filmId = 1L;
    private final Map<Long, Film> films = new HashMap<>();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film addFilm(@Valid @RequestBody Film film) {
        film.setId(filmId);
        films.put(filmId++, film);
        log.info("Фильм добавлен: {}", film);
        return film;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            log.error("Фильм {} имеет несуществующий id", film);
            throw new ValidationException("Фильм с таким id не существует");
        }
        films.put(film.getId(), film);
        log.info("Фильм обновлен: {}", film);
        return film;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> getAllFilms() {
        return films.values();
    }
}
