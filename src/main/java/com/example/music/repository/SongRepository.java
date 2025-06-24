package com.example.music.repository;

import com.example.music.model.Song;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface SongRepository extends R2dbcRepository<Song, Long> {

}
