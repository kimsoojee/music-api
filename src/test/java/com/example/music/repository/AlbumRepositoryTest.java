package com.example.music.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.test.StepVerifier;

@DataR2dbcTest
class AlbumRepositoryTest {

  @Autowired
  private AlbumRepository albumRepository;

  @Test
  @DisplayName("앨범 수 조회 성공 테스트")
  void getAlbumCountByYearAndArtist_ReturnsCorrectCounts() {
    StepVerifier.create(albumRepository.getAlbumCountByYearAndArtist(0L, 10))
      .expectNextMatches(albumCount ->
        albumCount.releaseYear().equals(2024) &&
          albumCount.artist().equals("Another Artist") &&
          albumCount.albumCount() == 1L
      )
      .expectNextMatches(albumCount ->
        albumCount.releaseYear().equals(2024) &&
          albumCount.artist().equals("Popular Artist") &&
          albumCount.albumCount() == 1L
      )
      .expectNextMatches(albumCount ->
        albumCount.releaseYear().equals(2024) &&
          albumCount.artist().equals("Third Artist") &&
          albumCount.albumCount() == 1L
      )
      .verifyComplete();
  }

  @Test
  @DisplayName("페이징 값 테스트")
  void getAlbumCountByYearAndArtist_WithPagination() {
    // 첫번째 페이지
    StepVerifier.create(albumRepository.getAlbumCountByYearAndArtist(0L, 2))
      .expectNextMatches(albumCount ->
        albumCount.releaseYear().equals(2024) &&
          albumCount.artist().equals("Another Artist")
      )
      .expectNextMatches(albumCount ->
        albumCount.releaseYear().equals(2024) &&
          albumCount.artist().equals("Popular Artist")
      )
      .verifyComplete();

    // 두번쨰 페이지
    StepVerifier.create(albumRepository.getAlbumCountByYearAndArtist(2L, 2))
      .expectNextMatches(albumCount ->
        albumCount.releaseYear().equals(2024) &&
          albumCount.artist().equals("Third Artist")
      )
      .verifyComplete();
  }
}
