DELETE FROM song_likes;
DELETE FROM songs;
DELETE FROM albums;

ALTER TABLE albums ALTER COLUMN id RESTART WITH 1;
ALTER TABLE songs ALTER COLUMN id RESTART WITH 1;
ALTER TABLE song_likes ALTER COLUMN id RESTART WITH 1;

INSERT INTO albums (title, artist, release_date, created_at, updated_at)
VALUES
    ('Popular Album', 'Popular Artist', '2024-03-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Another Album', 'Another Artist', '2024-06-20', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Third Album', 'Third Artist', '2024-09-10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO songs (album_id, title, length, likes, created_at, updated_at)
VALUES
    (1, 'Most Liked Song', '3:30', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'Second Most Liked', '4:00', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'Less Liked Song', '2:45', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO song_likes (song_id, liked_at)
VALUES
    (1, DATEADD('MINUTE', -30, CURRENT_TIMESTAMP)),
    (1, DATEADD('MINUTE', -25, CURRENT_TIMESTAMP)),
    (1, DATEADD('MINUTE', -20, CURRENT_TIMESTAMP)),

    (2, DATEADD('MINUTE', -15, CURRENT_TIMESTAMP)),
    (2, DATEADD('MINUTE', -10, CURRENT_TIMESTAMP)),

    (3, DATEADD('MINUTE', -5, CURRENT_TIMESTAMP));