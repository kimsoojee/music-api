package com.example.music.repository;

import com.example.music.model.SongLike;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface SongLikeRepository extends R2dbcRepository<SongLike, Long> {

}
