CREATE TABLE IF NOT EXISTS albums (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    artist VARCHAR(255) NOT NULL,
    release_date VARCHAR(50) NOT NULL,
    release_date DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    CONSTRAINT uk_album_artist_title UNIQUE (artist, title)
);

CREATE TABLE IF NOT EXISTS songs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    album_id BIGINT NOT NULL,
    song VARCHAR(255) NOT NULL,
    text CLOB,
    length VARCHAR(10),
    emotion VARCHAR(50),
    genre VARCHAR(255),
    musical_key VARCHAR(10),
    tempo DOUBLE,
    loudness DOUBLE,
    time_signature VARCHAR(10),
    explicit VARCHAR(10),
    popularity INTEGER DEFAULT 0,
    energy INTEGER,
    danceability INTEGER,
    positiveness INTEGER,
    speechiness INTEGER,
    liveness INTEGER,
    acousticness INTEGER,
    instrumentalness INTEGER,
    likes BIGINT DEFAULT 0,

    good_for_party BOOLEAN DEFAULT FALSE,
    good_for_work_study BOOLEAN DEFAULT FALSE,
    good_for_relaxation_meditation BOOLEAN DEFAULT FALSE,
    good_for_exercise BOOLEAN DEFAULT FALSE,
    good_for_running BOOLEAN DEFAULT FALSE,
    good_for_yoga_stretching BOOLEAN DEFAULT FALSE,
    good_for_driving BOOLEAN DEFAULT FALSE,
    good_for_social_gatherings BOOLEAN DEFAULT FALSE,
    good_for_morning_routine BOOLEAN DEFAULT FALSE,

    similar_artist1 VARCHAR(255),
    similar_song1 VARCHAR(255),
    similarity_score1 DOUBLE,
    similar_artist2 VARCHAR(255),
    similar_song2 VARCHAR(255),
    similarity_score2 DOUBLE,
    similar_artist3 VARCHAR(255),
    similar_song3 VARCHAR(255),
    similarity_score3 DOUBLE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    CONSTRAINT fk_album FOREIGN KEY (album_id) REFERENCES albums(id)
);

CREATE TABLE IF NOT EXISTS song_likes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    song_id BIGINT NOT NULL,
    liked_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    CONSTRAINT fk_song FOREIGN KEY (song_id) REFERENCES songs(id)
);

CREATE INDEX IF NOT EXISTS idx_album_release_date ON albums(release_date, artist);
CREATE INDEX IF NOT EXISTS idx_song_likes_time ON song_likes(liked_at, song_id);
