package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {

    Film addFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getAllFilms();

    List<Film> getPopularFilms(Long count);

    void likeFilm(Long id, Long userId);

    Film getFilmById(Long id);

    void unlikeFilm(Long filmId, Long id);
}
