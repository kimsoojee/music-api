package com.example.music.dto;

import com.example.music.repository.projection.TopLikedSong;
import java.time.LocalDate;

public record TopLikedSongResponse(
  String artist,
  String song,
  String length,
  String album,
  LocalDate releaseDate,
  Long likes,
  Long likeCount
) {

  public static TopLikedSongResponse of(TopLikedSong projection) {
    return new TopLikedSongResponse(
      projection.artist(),
      projection.song(),
      projection.length(),
      projection.album(),
      projection.releaseDate(),
      projection.likes(),
      projection.likeCount()
    );
  }
}
