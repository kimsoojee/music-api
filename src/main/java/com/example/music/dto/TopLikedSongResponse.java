package com.example.music.dto;

import com.example.music.repository.projection.TopLikedSong;

public record TopLikedSongResponse(
  String artist,
  String song,
  String length,
  String album,
  String releaseDate,
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
