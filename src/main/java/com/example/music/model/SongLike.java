package com.example.music.model;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("song_likes")
@Data
@NoArgsConstructor
public class SongLike {

  @Id
  private Long id;

  private Long songId;

  private LocalDateTime likedAt;

  public SongLike(Long songId) {
    this.songId = songId;
    this.likedAt = LocalDateTime.now();
  }
}
