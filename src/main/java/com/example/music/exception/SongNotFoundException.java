package com.example.music.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SongNotFoundException extends RuntimeException {

  public SongNotFoundException(Long songId) {
    super("존재하지 않는 songId: " + songId);
  }
}
