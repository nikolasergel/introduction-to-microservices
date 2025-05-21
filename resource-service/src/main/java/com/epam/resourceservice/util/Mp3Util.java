package com.epam.resourceservice.util;

import com.epam.resourceservice.exception.Mp3ParsingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Mp3Util {
    private static final Logger logger = LogManager.getLogger(Mp3Util.class);

    public static Metadata parseMetadata(byte[] mp3Data) {
        Tika tika = new Tika();
        Metadata metadata = new Metadata();
        try (InputStream stream = new ByteArrayInputStream(mp3Data)) {
            tika.parse(stream, metadata);
        } catch (IOException e) {
            throw new Mp3ParsingException("Cannot parse metadata in provided file.");
        }
        return metadata;
    }
}
