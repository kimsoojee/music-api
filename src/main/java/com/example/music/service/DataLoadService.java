package com.example.music.service;

import reactor.core.publisher.Mono;

public interface DataLoadService {

  Mono<Void> loadData();
}
