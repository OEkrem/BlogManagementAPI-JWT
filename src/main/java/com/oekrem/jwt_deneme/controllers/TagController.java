package com.oekrem.jwt_deneme.controllers;

import com.oekrem.jwt_deneme.dtos.requests.CreateTagRequest;
import com.oekrem.jwt_deneme.dtos.requests.UpdateTagRequest;
import com.oekrem.jwt_deneme.dtos.responses.TagResponse;
import com.oekrem.jwt_deneme.services.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getTags() {
        return ResponseEntity.ok(tagService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getTagById(@PathVariable UUID id) {
        return  ResponseEntity.ok(tagService.getTagById(id));
    }

    @PostMapping
    public ResponseEntity<TagResponse> createTag(@RequestBody @Valid CreateTagRequest createTagRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.createTag(createTagRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponse> updateTap(@PathVariable UUID id, @RequestBody @Valid UpdateTagRequest updateTagRequest){
        return ResponseEntity.ok(tagService.updateTag(id, updateTagRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
