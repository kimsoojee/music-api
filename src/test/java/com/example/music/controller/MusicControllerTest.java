package com.example.music.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.example.music.dto.AlbumCountResponse;
import com.example.music.dto.TopLikedSongResponse;
import com.example.music.service.MusicService;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = MusicController.class)
class MusicControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private MusicService musicService;

  @Nested
  @DisplayName("GetAlbumCountByYearAndArtist Tests")
  class getAlbumCountByYearAndArtistTests {

    @Test
    @DisplayName("앨범 수 조회 성공")
    void getAlbumCountByYearAndArtist_ValidParams_ReturnsOk() {
      var albumCount = new AlbumCountResponse(2025, "Test Artist", 5L);

      when(musicService.getAlbumCountByYearAndArtist(anyInt(), anyInt()))
        .thenReturn(Flux.just(albumCount));

      webTestClient.get()
        .uri("/api/music/statistics/albums/by-year-and-artist?page=0&size=10")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(AlbumCountResponse.class)
        .hasSize(1)
        .contains(albumCount);
    }

    @Test
    @DisplayName("잘못된 페이징 값으로 요청 시 에러")
    void getAlbumCountByYearAndArtist_InvalidPageSize_ReturnsBadRequest() {
      webTestClient.get()
        .uri("/api/music/statistics/albums/by-year-and-artist?page=0&size=0")
        .exchange()
        .expectStatus().isBadRequest();
    }
  }

  @Nested
  @DisplayName("LikeSong Tests")
  class LikeSongTests {

    @Test
    @DisplayName("좋아요 증가 성공")
    void likeSong_ValidId_ReturnsOk() {
      when(musicService.likeSong(anyLong())).thenReturn(Mono.empty());

      webTestClient.post()
        .uri("/api/music/songs/1/like")
        .exchange()
        .expectStatus().isOk();
    }

    @Test
    @DisplayName("최근 한시간 동안 좋아요 증가 top10 노래 조회")
    void getTopLikedSongsLastHour_ReturnsOk() {
      var topLikedSong = new TopLikedSongResponse(
        "Test Artist", "Test Song", "3:30",
        "Test Album", LocalDate.of(2025,1,1), 100L, 10L
      );

      when(musicService.getTopLikedSongsLastHour())
        .thenReturn(Flux.just(topLikedSong));

      webTestClient.get()
        .uri("/api/music/songs/recent-top-liked")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(TopLikedSongResponse.class)
        .hasSize(1)
        .contains(topLikedSong);
    }
  }
}
