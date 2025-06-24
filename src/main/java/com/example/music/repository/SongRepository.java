package com.example.music.repository;

import com.example.music.model.Song;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Mono;

public interface SongRepository extends R2dbcRepository<Song, Long> {

  @Query("UPDATE songs SET likes = likes + 1 WHERE id = :songId")
  Mono<Void> incrementLikes(@Param("songId") Long songId);
}
