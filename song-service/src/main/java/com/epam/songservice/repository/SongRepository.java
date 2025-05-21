package com.epam.songservice.repository;

import com.epam.songservice.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    @Query("SELECT s.id FROM Song s WHERE s.id IN :ids")
    List<Long> findExistingIdsByIds(@Param("ids") Iterable<Long> ids);
}
