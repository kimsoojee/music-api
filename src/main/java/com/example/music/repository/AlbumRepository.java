package com.example.music.repository;

import com.example.music.model.Album;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface AlbumRepository extends R2dbcRepository<Album, Long> {

}
