package ru.yandex.practicum.filmorate.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class, ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleBadRequest(final Exception e) {
        log.error(e.getMessage());
        return new ErrorResponseDto(400, "Invalid data");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleNotFound(final NotFoundException e) {
        return new ErrorResponseDto(404, e.getMessage());
    }
}
