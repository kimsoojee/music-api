package com.example.music.dto.internal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SimilarSong {

  private String artist;
  private String song;
  private double score;
}
