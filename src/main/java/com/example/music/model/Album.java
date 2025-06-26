package com.example.music.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("albums")
@NoArgsConstructor
public class Album {

  @Id
  private Long id;
  private String title;
  private String artist;
  private LocalDate releaseDate;
  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;
}
