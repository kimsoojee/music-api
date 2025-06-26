package com.example.music.service;

import com.example.music.dto.internal.SongJson;
import com.example.music.model.Album;
import com.example.music.model.Song;
import com.example.music.repository.AlbumRepository;
import com.example.music.repository.SongRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataLoadService {

  private final AlbumRepository albumRepository;
  private final SongRepository songRepository;
  private final ObjectMapper objectMapper;

  public Mono<Void> loadData() {
    return Mono.fromCallable(() ->
        new BufferedReader(new InputStreamReader(
            new ClassPathResource("dataset.json").getInputStream(), StandardCharsets.UTF_8)))
      .flatMapMany(reader -> Flux.fromStream(reader.lines())
        .doFinally(signal -> closeQuietly(reader)))
      .flatMap(this::parseLine)
      .flatMap(this::saveSongAndAlbum, 1000)
      .then();
  }

  private void closeQuietly(BufferedReader reader) {
    try {
      reader.close();
    } catch (IOException e) {
      log.warn("BufferedReader close 에러", e);
    }
  }

  private Mono<SongJson> parseLine(String line) {
    try {
      SongJson songJson = objectMapper.readValue(line, SongJson.class);
      if (songJson.getAlbumTitle() == null || songJson.getSongTitle() == null) {
        log.warn("json 에서 빈 값들로 인해 skip; album: {}, song: {},", songJson.getAlbumTitle(), songJson.getSongTitle());
        return Mono.empty();
      }
      return Mono.just(songJson);
    } catch (IOException e) {
      log.warn("SongJson 파싱 실패: {}", line, e);
      return Mono.empty();
    }
  }

  private Mono<Void> saveSongAndAlbum(SongJson songJson) {
    return getOrSaveAlbum(songJson)
      .flatMap(album -> {
        Mono<Song> existingOrSaved = songRepository.findByAlbumIdAndTitle(album.getId(), songJson.getSongTitle())
          .switchIfEmpty(
            songRepository.save(convertToSong(songJson, album.getId()))
              .onErrorResume(e -> {
                  if (isDuplicateKeyException(e)) {
                    log.info("중복 노래 존재; albumId: {}, songTitle: {}", album.getId(), songJson.getSongTitle());
                    return Mono.empty();
                  }
                  return Mono.error(e);
                }
              ));

        return existingOrSaved
          .then();
      })
      .onErrorResume(e -> {
        log.error("앨범 or 노래 처리 에러 {}, {}, {}", songJson.getArtist(), songJson.getAlbumTitle(), songJson.getSongTitle(), e);
        return Mono.empty();
      });
  }

  private Mono<Album> getOrSaveAlbum(SongJson songJson) {
    return albumRepository.findByArtistAndTitle(songJson.getArtist(), songJson.getAlbumTitle())
      .switchIfEmpty(
        albumRepository.save(new Album(songJson))
          .onErrorResume(e -> isDuplicateKeyException(e)
              ? albumRepository.findByArtistAndTitle(songJson.getArtist(), songJson.getAlbumTitle())
              : Mono.error(e))
      );
  }

  private Song convertToSong(SongJson songJson, Long albumId) {
    Song song = objectMapper.convertValue(songJson, Song.class);
    song.setSimilarSongs(songJson.getSimilarSongs());
    song.setAlbumId(albumId);
    return song;
  }

  private boolean isDuplicateKeyException(Throwable e) {
    return e instanceof DataIntegrityViolationException
      || (e.getCause() != null && e.getCause().getMessage().contains("constraint"));
  }
}
