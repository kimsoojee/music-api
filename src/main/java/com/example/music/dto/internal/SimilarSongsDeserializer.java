package com.example.music.dto.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SimilarSongsDeserializer extends JsonDeserializer<List<SimilarSong>> {

  @Override
  public List<SimilarSong> deserialize(JsonParser p, DeserializationContext ctxt)
    throws IOException {
    List<SimilarSong> result = new ArrayList<>();
    JsonNode node = p.getCodec().readTree(p);

    for (JsonNode item : node) {
      SimilarSong song = new SimilarSong();

      Iterator<Entry<String, JsonNode>> fields = item.fields();
      while (fields.hasNext()) {
        Map.Entry<String, JsonNode> entry = fields.next();
        String key = entry.getKey();
        String value = entry.getValue().asText();

        if (key.startsWith("Similar Artist")) {
          song.setArtist(value);
        } else if (key.startsWith("Similar Song")) {
          song.setSong(value);
        } else if (key.equals("Similarity Score")) {
          song.setScore(entry.getValue().asDouble());
        }
      }

      result.add(song);
    }

    return result;
  }
}
