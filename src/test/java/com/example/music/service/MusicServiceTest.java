package com.example.music.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.music.dto.AlbumCountResponse;
import com.example.music.dto.TopLikedSongResponse;
import com.example.music.exception.DatabaseOperationException;
import com.example.music.exception.SongNotFoundException;
import com.example.music.model.Song;
import com.example.music.model.SongLike;
import com.example.music.repository.AlbumRepository;
import com.example.music.repository.SongLikeRepository;
import com.example.music.repository.SongRepository;
import com.example.music.repository.projection.AlbumCount;
import com.example.music.repository.projection.TopLikedSong;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class MusicServiceTest {

  @Mock
  private SongRepository songRepository;

  @Mock
  private SongLikeRepository songLikeRepository;

  @Mock
  private AlbumRepository albumRepository;

  @Mock
  private TransactionalOperator transactionalOperator;

  @InjectMocks
  private MusicService musicService;

  private AlbumCount testAlbumCount;
  private TopLikedSong testTopLikedSong;
  private Song testSong;

  @BeforeEach
  void setUp() {
    testAlbumCount = new AlbumCount(2025, "Test Artist", 5L);

    testTopLikedSong = new TopLikedSong(1L, "Test Artist", "Test Song", "3:30",
      "Test Album", LocalDate.of(2025,1,1), 100L, 10L);

    testSong = new Song();
    testSong.setId(1L);
    testSong.setSong("Test Song");
  }

  @Nested
  @DisplayName("GetAlbumCountByYearAndArtist Tests")
  class GetAlbumCountByYearAndArtistTests {

    @Test
    @DisplayName("앨범 수 조회 - 성공")
    void getAlbumCountByYearAndArtist_ReturnData() {
      when(albumRepository.getAlbumCountByYearAndArtist(0L, 10))
        .thenReturn(Flux.just(testAlbumCount));

      StepVerifier.create(musicService.getAlbumCountByYearAndArtist(0, 10))
        .expectNext(new AlbumCountResponse(2025, "Test Artist", 5L))
        .verifyComplete();
    }

    @Test
    @DisplayName("앨범 수 조회 - 데이터베이스 에러")
    void getAlbumCountByYearAndArtist_DatabaseError() {
      when(albumRepository.getAlbumCountByYearAndArtist(0L, 10))
        .thenReturn(Flux.error(new RuntimeException("Database error")));

      StepVerifier.create(musicService.getAlbumCountByYearAndArtist(0, 10))
        .expectError(DatabaseOperationException.class)
        .verify();
    }
  }

  @Nested
  @DisplayName("LikeSong Tests")
  class LikeSongTests {

    @Test
    @DisplayName("좋아요 증가 - 성공")
    void likeSong_Success() {
      when(songRepository.findById(1L)).thenReturn(Mono.just(testSong));
      when(songRepository.incrementLikes(1L)).thenReturn(Mono.empty());
      when(songLikeRepository.save(any(SongLike.class))).thenReturn(Mono.empty());
      when(transactionalOperator.transactional(any(Mono.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

      StepVerifier.create(musicService.likeSong(1L))
        .verifyComplete();
    }

    @Test
    @DisplayName("좋아요 증가 - SongNotFound 에러")
    void likeSong_SongNotFound() {
      when(songRepository.findById(999L)).thenReturn(Mono.empty());

      StepVerifier.create(musicService.likeSong(999L))
        .expectError(SongNotFoundException.class)
        .verify();
    }

    @Test
    @DisplayName("좋아요 증가 - 데이터베이스 에러")
    void likeSong_DatabaseError() {
      when(songRepository.findById(1L)).thenReturn(Mono.just(testSong));
      when(songRepository.incrementLikes(1L))
        .thenReturn(Mono.error(new RuntimeException("Database error")));

      StepVerifier.create(musicService.likeSong(1L))
        .expectError(DatabaseOperationException.class)
        .verify();
    }
  }

  @Nested
  @DisplayName("TopLikedSongs Tests")
  class TopLikedSongsTests {

    @Test
    @DisplayName("최근 1시간 동안 top10 노래 조회 - 성공")
    void getTopLikedSongsLastHour_ReturnsData() {
      when(songRepository.findTopLikedSongsLastHour())
        .thenReturn(Flux.just(testTopLikedSong));

      StepVerifier.create(musicService.getTopLikedSongsLastHour())
        .expectNext(new TopLikedSongResponse("Test Artist", "Test Song", "3:30",
          "Test Album", LocalDate.of(2025,1,1), 100L, 10L))
        .verifyComplete();
    }

    @Test
    @DisplayName("최근 1시간 동안 top10 노래 조회 - 데이터베이스 에러")
    void getTopLikedSongsLastHour_DatabaseError() {
      when(songRepository.findTopLikedSongsLastHour())
        .thenReturn(Flux.error(new RuntimeException("Database error")));

      StepVerifier.create(musicService.getTopLikedSongsLastHour())
        .expectError(DatabaseOperationException.class)
        .verify();
    }
  }
}
