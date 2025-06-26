package com.example.music.repository;

import com.example.music.model.Album;
import com.example.music.repository.projection.AlbumCount;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlbumRepository extends R2dbcRepository<Album, Long> {

  @Query("""
    SELECT YEAR(a.release_date) AS release_year, 
           a.artist,
           COUNT(DISTINCT a.id) AS album_count
    FROM albums a
    GROUP BY YEAR(a.release_date), a.artist
    ORDER BY YEAR(a.release_date), a.artist
    LIMIT :size OFFSET :offset
    """)
  Flux<AlbumCount> getAlbumCountByYearAndArtist(
    @Param("offset") long offset, @Param("size") int size);

  Mono<Album> findByArtistAndTitle(String artist, String title);
}
