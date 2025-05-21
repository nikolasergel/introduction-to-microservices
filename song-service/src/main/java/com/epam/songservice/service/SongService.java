package com.epam.songservice.service;

import com.epam.songservice.dto.SongRequest;
import com.epam.songservice.dto.SongResponse;
import com.epam.songservice.entity.Song;
import com.epam.common.exception.EntityAlreadyExistsException;
import com.epam.common.exception.InvalidNumberException;
import com.epam.common.exception.NotFoundException;
import com.epam.songservice.mapper.SongMapper;
import com.epam.songservice.repository.SongRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Service
@Validated
public class SongService {
    private final SongRepository songRepository;
    private SongMapper songMapper;

    @Autowired
    public SongService(SongRepository songRepository, SongMapper mapper) {
        this.songRepository = songRepository;
        this.songMapper = mapper;
    }


    @Transactional
    public Song createSong(@Valid SongRequest songRequest) {
        Song song = songMapper.songRequestToSong(songRequest);
        if (songRepository.existsById(song.getId())) {
            throw new EntityAlreadyExistsException("Metadata for resource ID=" + song.getId() + " already exists");
        }
        return songRepository.save(song);
    }

    public SongResponse findSongById(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Song metadata for ID=" + id + " not found"));
        return songMapper.songToSongResponse(song);
    }

    public SongResponse updateSong(Long id, SongRequest songRequest) {
        Song song = songMapper.songRequestToSong(songRequest);
        song.setId(id);
        return songMapper.songToSongResponse(songRepository.save(song));
    }

    @Transactional
    public List<Long> deleteByIds(
            @Size(max = 200, message = "CSV string is too long: received ${validatedValue.length()} characters, maximum allowed is {max}")
            String ids
    ) {
        List<Long> idsToRemove = new ArrayList<>();
        for (String strId : ids.split(",")) {
            try {
                Long id = Long.parseLong(strId);
                idsToRemove.add(id);
            } catch (NumberFormatException ex) {
                throw new InvalidNumberException("Invalid ID format: '" + strId + "'. Only positive integers are allowed");
            }
        }

        idsToRemove = songRepository.findExistingIdsByIds(idsToRemove);
        songRepository.deleteAllByIdInBatch(idsToRemove);
        return idsToRemove;
    }
}
