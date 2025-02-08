package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Film {
    private Long id;
    @NotBlank(message = "Название не может быть пустым")
    private String name;
    @Size(max = 200, message = "Максимальная длина описания — 200 символов")
    private String description;
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    private Long duration;
    @Builder.Default
    private Set<Long> likes = new HashSet<>();

    @AssertTrue(message = "Дата релиза — не раньше 28 декабря 1895 года")
    public boolean isValidDate() {
        if (releaseDate == null) {
            return false;
        }
        return !releaseDate.isBefore(LocalDate.of(1895, 12, 28));
    }
}
