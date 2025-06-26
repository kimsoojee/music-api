package com.example.music.model;

import com.example.music.dto.internal.SongJson;
import com.fasterxml.jackson.annotation.JsonAlias;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("albums")
@NoArgsConstructor
public class Album {

  @Id
  private Long id;
  @JsonAlias("albumTitle")
  private String title;
  private String artist;
  private LocalDate releaseDate;
  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;

  public Album(SongJson songJson) {
    this.title = songJson.getAlbumTitle();
    this.artist = songJson.getArtist();
    this.releaseDate = songJson.getReleaseDate() == null
      ? LocalDate.of(1,1,1) : songJson.getReleaseDate();
  }
}
