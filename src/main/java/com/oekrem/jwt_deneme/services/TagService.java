package com.oekrem.jwt_deneme.services;

import com.oekrem.jwt_deneme.dtos.requests.CreateTagRequest;
import com.oekrem.jwt_deneme.dtos.requests.UpdateTagRequest;
import com.oekrem.jwt_deneme.dtos.responses.TagResponse;
import com.oekrem.jwt_deneme.models.Tag;

import java.util.List;
import java.util.UUID;

public interface TagService {

    List<TagResponse> getAll();
    TagResponse getTagById(UUID id);
    TagResponse createTag(CreateTagRequest createTagRequest);
    TagResponse updateTag(UUID id, UpdateTagRequest updateTagRequest);
    void deleteTag(UUID id);

    Tag validateTagById(UUID id);
    Tag validateTagByName(String name);

}
