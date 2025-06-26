package com.example.music.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("songs")
@NoArgsConstructor
public class Song {
  @Id
  private Long id;

  private Long albumId;

  @JsonAlias("songTitle")
  private String title;
  private String text;
  private String length;
  private String emotion;
  private String genre;

  private String musicalKey;
  private Double tempo;
  private Double loudness;

  private String timeSignature;
  private String explicit;
  private Integer popularity;
  private Integer energy;
  private Integer danceability;
  private Integer positiveness;
  private Integer speechiness;
  private Integer liveness;
  private Integer acousticness;
  private Integer instrumentalness;

  private Long likes = 0L;

  private Boolean goodForParty;
  private Boolean goodForWorkStudy;
  private Boolean goodForRelaxationMeditation;
  private Boolean goodForExercise;
  private Boolean goodForRunning;
  private Boolean goodForYogaStretching;
  private Boolean goodForDriving;
  private Boolean goodForSocialGatherings;
  private Boolean goodForMorningRoutine;

  private String similarArtist1;
  private String similarSong1;
  private Double similarityScore1;
  private String similarArtist2;
  private String similarSong2;
  private Double similarityScore2;
  private String similarArtist3;
  private String similarSong3;
  private Double similarityScore3;

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;
}
