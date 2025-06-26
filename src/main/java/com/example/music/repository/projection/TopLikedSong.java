package com.example.music.repository.projection;

import java.time.LocalDate;

public record TopLikedSong(
  Long id,
  String artist,
  String songTitle,
  String length,
  String albumTitle,
  LocalDate releaseDate,
  Long likes, // 총 좋아요 수
  Long likeCount  // 최근 1시간 동안 좋아요 수
) {

}
