package com.example.music.dto;

import com.example.music.repository.projection.AlbumCount;

public record AlbumCountResponse(
  Integer releaseYear,
  String artist,
  Long albumCount
) {

  public static AlbumCountResponse of(AlbumCount projection) {
    return new AlbumCountResponse(
      projection.releaseYear(),
      projection.artist(),
      projection.albumCount()
    );
  }
}
