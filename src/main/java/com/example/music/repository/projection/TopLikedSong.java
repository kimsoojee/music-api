package com.example.music.repository.projection;

import java.time.LocalDate;

public record TopLikedSong(
  Long id,
  String artist,
  String song,
  String length,
  String album,
  LocalDate releaseDate,
  Long likes, // 총 좋아요 수
  Long likeCount  // 최근 1시간 동안 좋아요 수
) {

}
