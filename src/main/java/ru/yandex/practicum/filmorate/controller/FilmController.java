package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private static final LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);
    private Long filmId = 1L;
    private final Map<Long, Film> films = new HashMap<>();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film addFilm(@RequestBody Film film) {
        Film validFilm = validateFilm(film);
        film.setId(filmId);
        films.put(filmId++, validFilm);
        log.info("Фильм добавлен: {}", validFilm);
        return validFilm;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Film updateFilm(@RequestBody Film film) {
        Film validFilm = validateFilm(film);

        if (!films.containsKey(validFilm.getId())) {
            log.error("Фильм {} имеет несуществующий id", validFilm);
            throw new ValidationException("Фильм с таким id не существует");
        }

        films.put(validFilm.getId(), validFilm);
        log.info("Фильм обновлен: {}", validFilm);
        return validFilm;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    private Film validateFilm(Film film) {
        if (film.getName().isBlank()) {
            log.error("Фильм {} имеет пустое поле name", film);
            throw new ValidationException("Название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.error("Фильм {} имеет в описании более 200 символов", film);
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(CINEMA_BIRTHDAY)) {
            log.error("Фильм {} имеет дату релиза раньше дня рождения кино", film);
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() < 0L) {
            log.error("Фильм {} имеет отрицательную продолжительность", film);
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
        return film;
    }
}
