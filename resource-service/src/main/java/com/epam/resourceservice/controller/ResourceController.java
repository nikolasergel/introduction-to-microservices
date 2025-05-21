package com.epam.resourceservice.controller;

import com.epam.common.annotation.PositiveInteger;
import com.epam.resourceservice.dto.ResourceResponse;
import com.epam.resourceservice.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/resources")
public class ResourceController {
    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping(value = "/{id}", produces = "audio/mpeg")
    public ResponseEntity<byte[]> getResource(
            @PositiveInteger
            @PathVariable(name = "id") String id) {
        byte[] resourceData = resourceService.getResourceById(id).getData();
        return ResponseEntity.ok(resourceData);
    }

    @PostMapping(consumes = "audio/mpeg")
    public ResponseEntity<ResourceResponse> createResource(@RequestBody byte[] audioData) throws Exception {
        ResourceResponse resourceResponse = resourceService.createResource(audioData);
        return ResponseEntity.ok(resourceResponse);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, List<Long>>> deleteResource(@RequestParam("id") String ids) {
        List<Long> response = resourceService.deleteResourcesByIds(ids);
        return ResponseEntity.ok(Map.of("ids", response));
    }

    @PutMapping(value = "/{id}", consumes = "audio/mpeg")
    public ResponseEntity<ResourceResponse> updateResource(
            @PositiveInteger @PathVariable("id") String id,
            @RequestBody byte[] audioData) throws IOException {
        ResourceResponse resourceResponse = resourceService.updateResourcesById(id, audioData);
        return ResponseEntity.ok(resourceResponse);
    }
}
