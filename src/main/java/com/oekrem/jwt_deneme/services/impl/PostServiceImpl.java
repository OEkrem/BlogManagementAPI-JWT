package com.oekrem.jwt_deneme.services.impl;

import com.oekrem.jwt_deneme.dtos.requests.CreatePostRequest;
import com.oekrem.jwt_deneme.dtos.requests.UpdatePostRequest;
import com.oekrem.jwt_deneme.dtos.responses.PostResponse;
import com.oekrem.jwt_deneme.models.Post;
import com.oekrem.jwt_deneme.models.Tag;
import com.oekrem.jwt_deneme.repositories.PostRepository;
import com.oekrem.jwt_deneme.services.CategoryService;
import com.oekrem.jwt_deneme.services.PostService;
import com.oekrem.jwt_deneme.services.TagService;
import com.oekrem.jwt_deneme.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private static final int MIN_READTIME_PER_WORD = 200; // milisaniye türünden girilecek veri

    private final PostRepository postRepository;
    private final TagService tagService;
    private final CategoryService categoryService;
    private final UserService userService;

    @Override
    @Transactional
    public List<PostResponse> getAll(UUID categoryId, UUID tagId) {
        List<Post> posts = new ArrayList<>();
        if(categoryId != null && tagId != null) {
            posts = postRepository.findAllPublishedByCategoryAndTagId(categoryId, tagId);
        }
        else if(categoryId == null && tagId != null) {
            posts = postRepository.findAllPublishedByTag(tagId);
        }
        else if(categoryId != null && tagId == null) {
            posts = postRepository.findAllPublishedByCategory(categoryId);
        }
        else if(categoryId == null && tagId == null) {
            posts = postRepository.findAllPublished();
        }

        return toPostResponsesFromPosts(posts);
    }

    @Override
    @Transactional
    public List<PostResponse> getAllDrafts(UUID userId) {
        List<Post> posts = postRepository.findAllDraftsByUserId(userId);
        return toPostResponsesFromPosts(posts);
    }

    @Override
    @Transactional
    public PostResponse getPostById(UUID id) {
        Post post = validatePostById(id);
        return toPostResponseFromPost(post);
    }

    @Override
    @Transactional
    public PostResponse createPost(CreatePostRequest createPostRequest) {
        Post post = toPostFromCreateRequest(createPostRequest);
        post.setReadingTime(calculateReadingTime(post.getContent()));
        Post savedPost = postRepository.save(post);
        return toPostResponseFromPost(savedPost);
    }

    @Override
    @Transactional
    public PostResponse updatePost(UUID id, UpdatePostRequest updatePostRequest) {
        Post post2 = validatePostById(id);
        Post post = toPostFromUpdateRequest(updatePostRequest);
        post.setId(id);
        post.setReadingTime(calculateReadingTime(post.getContent()));
        post.setCreatedAt(post2.getCreatedAt());
        Post updatedPost = postRepository.save(post);
        return toPostResponseFromPost(updatedPost);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        postRepository.delete(validatePostById(id));
    }

    @Override
    public Post validatePostById(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Post not found"));
    }

    private int calculateReadingTime(String content) {
        int wordNumber = content.split(" ").length;
        return (wordNumber * MIN_READTIME_PER_WORD)/1000; // saniye türünden dönüyor
    }


    // MAPPER ------------------------------------------------------------------------------------------------
    private List<PostResponse> toPostResponsesFromPosts(List<Post> posts) {
        return posts.stream()
                .map(this::toPostResponseFromPost)
                .toList();
    }

    private PostResponse toPostResponseFromPost(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .readingTime(post.getReadingTime())
                .postStatus(post.getPostStatus())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .userId(post.getUser().getId())
                .tagsIds(post.getTags().stream().map(Tag::getId).collect(Collectors.toSet()))
                .categoryId(post.getCategory().getId())
                .build();
    }

    private Post toPostFromCreateRequest(CreatePostRequest createPostRequest) {
        Set<Tag> tags = createPostRequest.getTagIds().stream()
                .map(tagService::validateTagById)
                .collect(Collectors.toSet());
        return Post.builder()
                .title(createPostRequest.getTitle())
                .content(createPostRequest.getContent())
                .postStatus(createPostRequest.getPostStatus())
                .user(userService.validateUserById(createPostRequest.getUser_id()))
                .tags(tags)
                .category(categoryService.validateCtegoryById(createPostRequest.getCategory_id()))
                .build();
    }

    private Post toPostFromUpdateRequest(UpdatePostRequest updatePostRequest) {
        Set<Tag> tags = updatePostRequest.getTagIds().stream()
                .map(tagService::validateTagById)
                .collect(Collectors.toSet());
        return Post.builder()
                .title(updatePostRequest.getTitle())
                .content(updatePostRequest.getContent())
                .postStatus(updatePostRequest.getPostStatus())
                .user(userService.validateUserById(updatePostRequest.getUser_id()))
                .tags(tags)
                .category(categoryService.validateCtegoryById(updatePostRequest.getCategory_id()))
                .build();
    }

}
