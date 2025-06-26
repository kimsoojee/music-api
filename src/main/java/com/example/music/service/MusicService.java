package com.example.music.service;

import com.example.music.dto.response.AlbumCountResponse;
import com.example.music.dto.response.TopLikedSongResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MusicService {

  Flux<AlbumCountResponse> getAlbumCountByYearAndArtist(int page, int size);

  Mono<Void> likeSong(Long songId);

  Flux<TopLikedSongResponse> getTopLikedSongsLastHour();
}
