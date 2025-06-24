package com.example.music.controller;

import com.example.music.dto.AlbumCountResponse;
import com.example.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/music")
@RequiredArgsConstructor
public class MusicController {

  private final MusicService musicService;

  @GetMapping("/statistics/albums/by-year-and-artist")
  public Flux<AlbumCountResponse> getAlbumCountByYearAndArtist(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
    return musicService.getAlbumCountByYearAndArtist(page, size);
  }

  @PostMapping("/songs/{id}/like")
  public Mono<ResponseEntity<Void>> likeSong(@PathVariable Long id) {
    return musicService.likeSong(id)
      .then(Mono.just(ResponseEntity.ok().build()));
  }
}
