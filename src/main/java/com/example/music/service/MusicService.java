package com.example.music.service;

import com.example.music.dto.AlbumCountResponse;
import com.example.music.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class MusicService {

  private final AlbumRepository albumRepository;

  public Flux<AlbumCountResponse> getAlbumCountByYearAndArtist(int page, int size) {
    long offset = (long) page * size;
    return albumRepository.getAlbumCountByYearAndArtist(offset, size)
      .map(AlbumCountResponse::of);
  }
}
