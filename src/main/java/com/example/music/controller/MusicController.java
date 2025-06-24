package com.example.music.controller;

import com.example.music.dto.AlbumCountResponse;
import com.example.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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
}
