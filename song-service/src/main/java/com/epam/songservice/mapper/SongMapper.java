package com.epam.songservice.mapper;

import com.epam.songservice.dto.SongRequest;
import com.epam.songservice.dto.SongResponse;
import com.epam.songservice.entity.Song;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SongMapper {
    Song songRequestToSong(SongRequest songRequest);
    SongResponse songToSongResponse(Song song);
}
