package com.epam.resourceservice.mapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.metadata.Metadata;

import java.util.Map;

public class SongMetadataMapper {
    private static final Logger logger = LogManager.getLogger(SongMetadataMapper.class);
    private static final String NAME = "dc:title";
    private static final String ALBUM = "xmpDM:album";
    private static final String ARTIST = "xmpDM:artist";
    private static final String DURATION = "xmpDM:duration";
    private static final String YEAR = "xmpDM:releaseDate";

    public static Map<String, String> mapSongMetadata(Metadata metadata){
        return Map.of(
                "name", getValue(metadata, NAME),
                "album", getValue(metadata, ALBUM),
                "artist", getValue(metadata, ARTIST),
                "duration", formatSecondsToMMSS(getValue(metadata, DURATION)),
                "year", getValue(metadata, YEAR)
                );
    };

    private static String getValue(Metadata metadata, String key){
        String value = metadata.get(key);
        return value == null ? "" : value;
    }

    private static String formatSecondsToMMSS(String secondsStr) {
        double secondsDouble = 0;
        try {
             secondsDouble = Double.parseDouble(secondsStr);
        } catch (NumberFormatException ex){
            logger.debug("Cannot parse seconds value into double: {}", secondsStr, ex);
        }
        long totalSeconds = (long) secondsDouble;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
