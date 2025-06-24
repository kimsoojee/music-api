package com.example.music.exception;

import com.example.music.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Mono<ErrorResponse> handleValidationExceptions(ConstraintViolationException ex) {
    return Mono.just(new ErrorResponse("VALIDATION_ERROR", ex.getMessage()));
  }

  @ExceptionHandler(SongNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Mono<ErrorResponse> handleSongNotFound(SongNotFoundException ex) {
    return Mono.just(new ErrorResponse("SONG_NOT_FOUND", ex.getMessage()));
  }

  @ExceptionHandler(DatabaseOperationException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Mono<ErrorResponse> handleDatabaseError(DatabaseOperationException ex) {
    return Mono.just(new ErrorResponse("DATABASE_ERROR", ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Mono<ErrorResponse> handleUnexpectedError(Exception ex) {
    return Mono.just(new ErrorResponse("INTERNAL_SERVER_ERROR", "내부 서버 오류가 발생했습니다."));
  }
}
