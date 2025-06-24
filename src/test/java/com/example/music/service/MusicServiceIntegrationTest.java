package com.example.music.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import com.example.music.model.Song;
import com.example.music.model.SongLike;
import com.example.music.repository.AlbumRepository;
import com.example.music.repository.SongLikeRepository;
import com.example.music.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class MusicServiceIntegrationTest {

  @Autowired
  private MusicService musicService;

  @Autowired
  private SongRepository songRepository;

  @Autowired
  private SongLikeRepository songLikeRepository;

  @Autowired
  private TransactionalOperator transactionalOperator;

  @Autowired
  private AlbumRepository albumRepository;

  @BeforeEach
  void setUp() {
    songLikeRepository.deleteAll().block();
  }

  @Test
  @DisplayName("좋아요 증가 - 동시 요청 테스트")
  void likeSong_ConcurrentRequests() {
    int concurrentRequests = 10;
    long songId = 1L;
    Song initialSong = songRepository.findById(songId).block();
    long initialLikes = initialSong != null ? initialSong.getLikes() : 0L;

    // 동시 요청
    Flux<Void> concurrentLikes = Flux.range(0, concurrentRequests)
      .flatMap(i -> musicService.likeSong(songId), concurrentRequests);
    StepVerifier.create(concurrentLikes)
      .verifyComplete();

    // 좋아요 수 확인
    StepVerifier.create(songRepository.findById(songId))
      .assertNext(song -> assertThat(song.getLikes()).isEqualTo(initialLikes + concurrentRequests))
      .verifyComplete();

    // songlike 확인
    StepVerifier.create(songLikeRepository.findAll())
      .expectNextCount(concurrentRequests)
      .verifyComplete();
  }

  @Test
  @DisplayName("좋아요 증가 - 단일 요청 처리 통합 테스트")
  void likeSong_SingleRequest() {
    long songId = 1L;
    Song initialSong = songRepository.findById(songId).block();
    long initialLikes = initialSong != null ? initialSong.getLikes() : 0L;

    // 좋아요 요청
    StepVerifier.create(musicService.likeSong(songId))
      .verifyComplete();

    // 좋아요 수 확인
    StepVerifier.create(songRepository.findById(songId))
      .assertNext(song -> assertThat(song.getLikes()).isEqualTo(initialLikes + 1))
      .verifyComplete();

    // songlike 확인
    StepVerifier.create(songLikeRepository.findAll())
      .assertNext(like -> {
        assertThat(like.getSongId()).isEqualTo(songId);
        assertThat(like.getLikedAt()).isNotNull();
      })
      .verifyComplete();
  }

  @Test
  @DisplayName("좋아요 증가 - 에러 시 롤백 테스트")
  void likeSong_Rollback() {
    long songId = 1L;
    Song initialSong = songRepository.findById(songId).block();
    long initialLikes = initialSong != null ? initialSong.getLikes() : 0L;

    // songlike 저장 시 강제로 에러 발생시킴
    SongLikeRepository spyRepository = Mockito.spy(SongLikeRepository.class);
    Mockito.doReturn(Mono.error(new RuntimeException("저장 실패")))
      .when(spyRepository).save(any(SongLike.class));

    MusicService testMusicService = new MusicService(
      albumRepository, spyRepository, songRepository, transactionalOperator);

    // 에러 발생했는지 확인
    StepVerifier.create(testMusicService.likeSong(songId))
      .expectError()
      .verify();

    // 좋아요 증가 안함
    StepVerifier.create(songRepository.findById(songId))
      .expectNextMatches(song -> song.getLikes() == initialLikes)
      .verifyComplete();
  }
}
