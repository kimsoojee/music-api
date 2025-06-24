package com.example.music.repository.projection;

public record AlbumCount(
  Integer releaseYear,
  String artist,
  Long albumCount
) {

}
