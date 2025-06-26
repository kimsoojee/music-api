package com.example.music.dto.response;

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
      projection.songTitle(),
      projection.length(),
      projection.albumTitle(),
      projection.releaseDate(),
      projection.likes(),
      projection.likeCount()
    );
  }
}
