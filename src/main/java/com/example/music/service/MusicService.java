package com.example.music.service;

import com.example.music.dto.response.AlbumCountResponse;
import com.example.music.dto.response.TopLikedSongResponse;
import com.example.music.exception.DatabaseOperationException;
import com.example.music.exception.SongNotFoundException;
import com.example.music.model.SongLike;
import com.example.music.repository.AlbumRepository;
import com.example.music.repository.SongLikeRepository;
import com.example.music.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MusicService {

  private final AlbumRepository albumRepository;
  private final SongLikeRepository songLikeRepository;
  private final SongRepository songRepository;
  private final TransactionalOperator transactionalOperator;

  public Flux<AlbumCountResponse> getAlbumCountByYearAndArtist(int page, int size) {
    long offset = (long) page * size;
    return albumRepository.getAlbumCountByYearAndArtist(offset, size)
      .map(AlbumCountResponse::of)
      .onErrorMap(ex -> new DatabaseOperationException("앨범 수 조회 중 DB 에러 발생:", ex));
  }

  public Mono<Void> likeSong(Long songId) {
    return songRepository.findById(songId)
      .switchIfEmpty(Mono.error(new SongNotFoundException(songId)))
      .flatMap(song -> {
        SongLike songLike = new SongLike(songId);
        return transactionalOperator.transactional(
          songRepository.incrementLikes(songId)
            .then(songLikeRepository.save(songLike)));
      })
      .onErrorMap(ex -> ex instanceof SongNotFoundException ? ex
        : new DatabaseOperationException("좋아요 저장 중 DB 에러 발생:", ex))
      .then();
  }

  public Flux<TopLikedSongResponse> getTopLikedSongsLastHour() {
    return songRepository.findTopLikedSongsLastHour()
      .map(TopLikedSongResponse::of)
      .onErrorMap(ex -> new DatabaseOperationException("TopLikedSongs 조회 중 DB 에러 발생:", ex));
  }
}
