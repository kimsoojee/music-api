package com.example.music.repository;

import com.example.music.model.Song;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.test.StepVerifier;

@DataR2dbcTest
class SongRepositoryTest {

  @Autowired
  private SongRepository songRepository;

  @Test
  @DisplayName("좋아요 증가 테스트")
  void incrementLikes() {
    long songId = 1L;
    Song initialSong = songRepository.findById(songId).block();
    long initialLikes = initialSong != null ? initialSong.getLikes() : 0L;

    StepVerifier.create(songRepository.incrementLikes(1L)
        .then(songRepository.findById(1L)))
      .expectNextMatches(song -> song.getLikes() == initialLikes + 1)
      .verifyComplete();
  }

  @Test
  @DisplayName("최근 좋아요 top10 조회 테스트")
  void findTopLikedSongsLastHour() {
    StepVerifier.create(songRepository.findTopLikedSongsLastHour())
      .expectNextMatches(topLikedSong ->
        topLikedSong.artist().equals("Popular Artist") &&
          topLikedSong.songTitle().equals("Most Liked Song") &&
          topLikedSong.length().equals("3:30") &&
          topLikedSong.albumTitle().equals("Popular Album") &&
          topLikedSong.releaseDate().equals(LocalDate.of(2024,3,15)) &&
          topLikedSong.likeCount() == 3L
      )
      .expectNextCount(2) // 나머지 두개
      .verifyComplete();
  }
}
