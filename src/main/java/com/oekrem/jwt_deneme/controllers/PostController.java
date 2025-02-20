package com.oekrem.jwt_deneme.controllers;

import com.oekrem.jwt_deneme.dtos.requests.CreatePostRequest;
import com.oekrem.jwt_deneme.dtos.requests.UpdatePostRequest;
import com.oekrem.jwt_deneme.dtos.responses.PostResponse;
import com.oekrem.jwt_deneme.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(
            @RequestParam(required = false) UUID category_id,
            @RequestParam(required = false) UUID tag_id
    ) {
        return ResponseEntity.ok(postService.getAll(category_id, tag_id));
    }

    @GetMapping("/drafts/{user_id}")
    public ResponseEntity<List<PostResponse>> getAllDrafts(@PathVariable UUID user_id) {
        return ResponseEntity.ok(postService.getAllDrafts(user_id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable UUID id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid CreatePostRequest createPostRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(createPostRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable UUID id, @RequestBody @Valid UpdatePostRequest updatePostRequest) {
        return ResponseEntity.ok(postService.updatePost(id, updatePostRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
