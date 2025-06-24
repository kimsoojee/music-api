package com.example.music.repository;

import com.example.music.model.Album;
import com.example.music.repository.projection.AlbumCount;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

public interface AlbumRepository extends R2dbcRepository<Album, Long> {

  @Query("""
    SELECT CAST(RIGHT(a.release_date, 4) AS INT) AS release_year, 
           a.artist,
           COUNT(DISTINCT a.id) AS album_count
    FROM albums a
    GROUP BY RIGHT(a.release_date, 4), a.artist
    ORDER BY RIGHT(a.release_date, 4), a.artist
    LIMIT :size OFFSET :offset
    """)
  Flux<AlbumCount> getAlbumCountByYearAndArtist(
    @Param("offset") long offset, @Param("size") int size);
}
