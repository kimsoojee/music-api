package com.example.music.repository;

import com.example.music.model.Song;
import com.example.music.repository.projection.TopLikedSong;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SongRepository extends R2dbcRepository<Song, Long> {

  @Query("UPDATE songs SET likes = likes + 1 WHERE id = :songId")
  Mono<Void> incrementLikes(@Param("songId") Long songId);

  @Query("""
    WITH recent_likes AS (
        SELECT song_id, COUNT(id) as like_count
        FROM song_likes
        WHERE liked_at >= DATEADD('HOUR', -1, CURRENT_TIMESTAMP)
        GROUP BY song_id
    )
    SELECT s.id, s.song, s.length, s.likes, a.artist,
           a.title as album, a.release_date,
           rl.like_count as like_count
    FROM recent_likes rl
    INNER JOIN songs s ON s.id = rl.song_id
    INNER JOIN albums a ON a.id = s.album_id
    ORDER BY rl.like_count DESC
    LIMIT 10
    """)
  Flux<TopLikedSong> findTopLikedSongsLastHour();
}
