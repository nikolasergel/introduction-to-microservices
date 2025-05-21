package com.epam.songservice.controller;

import com.epam.songservice.dto.SongRequest;
import com.epam.songservice.dto.SongResponse;
import com.epam.songservice.entity.Song;
import com.epam.songservice.service.SongService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> createSong(@RequestBody @Valid SongRequest songRequest) {
        Song savedSong = songService.createSong(songRequest);
        return new ResponseEntity<>(Map.of("id", savedSong.getId()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongResponse> getSong(@PathVariable("id") Long id) {
        return ResponseEntity.ok(songService.findSongById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongResponse> getSong(@PathVariable("id") Long id, @RequestBody @Valid SongRequest songRequest) {
        return ResponseEntity.ok(songService.updateSong(id, songRequest));
    }

    @DeleteMapping
    public ResponseEntity<Map<String, List<Long>>> getSong(@RequestParam("id") String csvIds) {
        List<Long> deletedIds = songService.deleteByIds(csvIds);
        return ResponseEntity.ok(Map.of("ids", deletedIds));
    }
}
