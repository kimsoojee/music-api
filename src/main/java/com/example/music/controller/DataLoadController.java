package com.example.music.controller;

import com.example.music.service.DataLoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class DataLoadController {

  private final DataLoadService dataLoaderService;

  @GetMapping("/load")
  public Mono<Void> loadData() {
    return dataLoaderService.loadData();
  }
}
