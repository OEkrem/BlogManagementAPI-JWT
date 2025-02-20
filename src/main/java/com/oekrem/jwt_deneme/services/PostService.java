package com.oekrem.jwt_deneme.services;

import com.oekrem.jwt_deneme.dtos.requests.CreatePostRequest;
import com.oekrem.jwt_deneme.dtos.requests.UpdatePostRequest;
import com.oekrem.jwt_deneme.dtos.responses.PostResponse;
import com.oekrem.jwt_deneme.models.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {

    List<PostResponse> getAll(UUID categoryId, UUID tagId);
    List<PostResponse> getAllDrafts(UUID userId);
    PostResponse getPostById(UUID id);
    PostResponse createPost(CreatePostRequest createPostRequest);
    PostResponse updatePost(UUID id, UpdatePostRequest updatePostRequest);
    void deleteById(UUID id);

    Post validatePostById(UUID id);


}
