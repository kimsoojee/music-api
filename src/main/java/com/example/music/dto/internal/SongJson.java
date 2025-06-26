package com.example.music.dto.internal;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class SongJson {

  @JsonAlias("Artist(s)")
  private String artist;

  @JsonAlias("song")
  private String songTitle;

  private String text;

  @JsonAlias("Length")
  private String length;

  private String emotion;
  @JsonAlias("Genre")
  private String genre;

  @JsonAlias("Album")
  private String albumTitle;

  @JsonAlias("Release Date")
  private LocalDate releaseDate;

  @JsonAlias("Key")
  private String musicalKey;

  @JsonAlias("Tempo")
  private Double tempo;

  @JsonAlias("Loudness (db)")
  private Double loudness;

  @JsonAlias("Time signature")
  private String timeSignature;

  @JsonAlias("Explicit")
  private String explicit;

  @JsonAlias("Popularity")
  private Integer popularity;

  @JsonAlias("Energy")
  private Integer energy;

  @JsonAlias("Danceability")
  private Integer danceability;

  @JsonAlias("Positiveness")
  private Integer positiveness;

  @JsonAlias("Speechiness")
  private Integer speechiness;

  @JsonAlias("Liveness")
  private Integer liveness;

  @JsonAlias("Acousticness")
  private Integer acousticness;

  @JsonAlias("Instrumentalness")
  private Integer instrumentalness;

  @JsonAlias("Good For Party")
  private Boolean goodForParty = false;
  @JsonAlias("Good For Work Study")
  private Boolean goodForWorkStudy = false;
  @JsonAlias("Good For RelaxationMeditation")
  private Boolean goodForRelaxationMeditation = false;
  @JsonAlias("Good For Exercise")
  private Boolean goodForExercise = false;
  @JsonAlias("Good For Running")
  private Boolean goodForRunning = false;
  @JsonAlias("Good For Yoga Stretching")
  private Boolean goodForYogaStretching = false;
  @JsonAlias("Good For Driving")
  private Boolean goodForDriving = false;
  @JsonAlias("Good For Social Gatherings")
  private Boolean goodForSocialGatherings = false;
  @JsonAlias("Good For Morning Routine")
  private Boolean goodForMorningRoutine = false;
  @JsonAlias("Similar Songs")
  @JsonDeserialize(using = SimilarSongsDeserializer.class)
  private List<SimilarSong> similarSongs = new ArrayList<>();
}
