# Music API

## Features
- 파일 데이터 저장
- 연도 & 가수별 발매 앨범 수 조회
- 노래별 '좋아요' 증가
- 최근 1시간 동안 '좋아요' 증가 Top 10 조회

## Tech Stack
- Java 21
- Spring Boot 3.2.3
- Spring WebFlux
- Spring Data R2DBC
- H2 Database

## API Endpoints

### 파일 데이터 저장
```http
GET /api/data/load
```

### 연도 & 가수별 발매 앨범 수 페이지 단위 조회
```http
GET /api/music/statistics/albums/by-year-and-artist?page=0&size=10
```

### 노래별 좋아요 증가 및 좋아요 히스토리 저장
```http
POST /api/music/songs/{id}/like
```

### 지난 한시간 동안 좋아요 증가한 노래 TOP 10 조회
```http
GET /api/music/songs/recent-top-liked
```

## 프로젝트 실행 방법

### Prerequisites
- Java 21
- Gradle

### 프로젝트 실행

1. 프로젝트 빌드
```bash
./gradlew build
```

2. 기초 데이터 파일 저장

- `main/java/resource` 에 기초 데이터 파일을 `dataset.json` 이름으로 저장
- [파일 링크](https://www.kaggle.com/datasets/devdope/900k-spotify?select=900k+Definitive+Spotify+Dataset.json)

2. 프로젝트 실행
```bash
./gradlew bootRun
```

## 테이블 정보

### Albums

| Column       | Type         | Constraints                 |
|--------------|--------------|-----------------------------|
| id           | BIGINT       | PRIMARY KEY, AUTO_INCREMENT |
| title        | VARCHAR(255) | NOT NULL                    |
| artist       | VARCHAR(800) | NOT NULL                    |
| release_date | DATE         | NOT NULL                    |
| created_at   | TIMESTAMP    |                             |
| updated_at   | TIMESTAMP    |                             |


### Songs

| Column     | Type         | Constraints                       |
|------------|--------------|-----------------------------------|
| id         | BIGINT       | PRIMARY KEY, AUTO_INCREMENT       |
| album_id   | BIGINT       | NOT NULL, FOREIGN KEY (albums.id) |
| title      | VARCHAR(255) | NOT NULL                          |
| likes      | BIGINT       | DEFAULT 0                         |
| created_at | TIMESTAMP    |                                   |
| updated_at | TIMESTAMP    |                                   |
| ...        | ...          | ...                               |
> Songs 테이블에는 필드가 많아서 필요한 정보만 적고 나머지는 생략


### SongLikes

| Column   | Type       | Constraints                      |
|----------|------------|----------------------------------|
| id       | BIGINT     | PRIMARY KEY, AUTO_INCREMENT      |
| song_id  | BIGINT     | NOT NULL, FOREIGN KEY (songs.id) |
| liked_at | TIMESTAMP  | NOT NULL                         |

## Tests
```bash
./gradlew test
```
- Unit Tests
    - MusicServiceTest.java
- Integration Tests
    - AlbumRepository.java
    - SongRepository.java
    - MusicServiceIntegrationTest.java
- Functional Tests
    - MusicControllerTest.java

## Error Handling

GlobalExceptionHandler 처리하는 에러들:
- 요청한 songId가 없는 경우
- DB 관련 에러
- Validation 에러
- Exception 에러
