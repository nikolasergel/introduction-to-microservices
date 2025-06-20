package com.epam.songservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "songs")
public class Song {
    @Id
    private Long id;
    private String name;
    private String artist;
    private String album;
    private String duration;

    @Column(name = "release_year")
    private String year;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song song)) return false;
        return Objects.equals(name, song.name) && Objects.equals(artist, song.artist) && Objects.equals(album, song.album) && Objects.equals(duration, song.duration) && Objects.equals(year, song.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, artist, album, duration, year);
    }
}
