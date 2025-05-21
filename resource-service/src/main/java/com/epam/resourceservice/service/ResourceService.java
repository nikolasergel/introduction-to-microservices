package com.epam.resourceservice.service;

import com.epam.resourceservice.entity.Resource;
import com.epam.resourceservice.dto.ResourceResponse;
import com.epam.common.exception.InvalidNumberException;
import com.epam.common.exception.NotFoundException;
import com.epam.resourceservice.mapper.ResourceMapper;
import com.epam.resourceservice.mapper.SongMetadataMapper;
import com.epam.resourceservice.repository.ResourceRepository;
import com.epam.resourceservice.util.Mp3Util;
import jakarta.validation.constraints.Size;
import org.apache.tika.metadata.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Validated
@Service
public class ResourceService {
    private final ResourceRepository resourceRepository;
    private final RestTemplate restTemplate;
    private final String songServiceUrl;
    private final ResourceMapper resourceMapper;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository, RestTemplate restTemplate,
                           @Value("${song.service.url}") String songServiceUrl,
                           ResourceMapper mapper) {
        this.resourceRepository = resourceRepository;
        this.restTemplate = restTemplate;
        this.songServiceUrl = songServiceUrl;
        this.resourceMapper = mapper;
    }

    public Resource getResourceById(String id) {
        Long longId = Long.parseLong(id);
        return resourceRepository.findById(longId).orElseThrow(() -> new NotFoundException("Song metadata for ID=" + id + " not found"));
    }

    @Transactional
    public List<Long> deleteResourcesByIds(
            @Size(max = 200, message = "CSV string is too long: received ${validatedValue.length()} characters, maximum allowed is {max}")
            String csvIds) {
        List<Long> idsToRemove = new ArrayList<>();
        for(String strId : csvIds.split(",")){
            try{
                idsToRemove.add(Long.parseLong(strId));
            } catch (NumberFormatException ex){
                throw new InvalidNumberException("Invalid ID format: '" + strId + "'. Only positive integers are allowed");
            }
        }
        idsToRemove = resourceRepository.findExistingIdsByIds(idsToRemove);
        resourceRepository.deleteAllById(idsToRemove);
        restTemplate.delete(songServiceUrl + "?id=" + csvIds);
        return idsToRemove;
    }

    public ResourceResponse updateResourcesById(String id, byte[] audioData) throws IOException {
        return saveResource(id, audioData);
    }

    public ResourceResponse createResource(byte[] audioData) throws IOException {
        return saveResource(null, audioData);
    }

    private ResourceResponse saveResource(String id, byte[] audioData) throws IOException {
        Resource resource = new Resource();
        resource.setData(audioData);
        if(id != null){
            resource.setId(Long.parseLong(id));
        }
        Metadata metadata = Mp3Util.parseMetadata(resource.getData());
        var map = new HashMap<>(SongMetadataMapper.mapSongMetadata(metadata));
        resource = resourceRepository.save(resource);
        map.put("id", resource.getId().toString());
        //send metadata to another service
        ResponseEntity<String> response = restTemplate.postForEntity(songServiceUrl, map, String.class);
        return resourceMapper.resourceToResourceResponse(resource);
    }
}
