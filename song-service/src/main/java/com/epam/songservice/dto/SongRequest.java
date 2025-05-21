package com.epam.songservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongRequest {
    @Id
    @NotNull(message = "Song id is required")
    private Long id;

    @Size(min = 1, max = 100)
    @NotNull(message = "Song name is required")
    private String name;

    @Size(min = 1, max = 100)
    @NotNull(message = "Song artist is required")
    private String artist;

    @Size(min = 1, max = 100)
    @NotNull(message = "Song album is required")
    private String album;

    @Pattern(regexp = "^[0-5][0-9]:[0-5][0-9]$", message = "Duration must be in mm:ss format with leading zeros")
    @NotNull(message = "Song duration is required")
    private String duration;

    @Pattern(regexp = "(19|20)\\d\\d", message = "Year must be between 1900 and 2099")
    @NotNull(message = "Song year is required")
    @Column(name = "release_year")
    private String year;
}
