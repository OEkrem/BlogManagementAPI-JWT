package com.oekrem.jwt_deneme.services.impl;

import com.oekrem.jwt_deneme.dtos.mappers.TagMapper;
import com.oekrem.jwt_deneme.dtos.requests.CreateTagRequest;
import com.oekrem.jwt_deneme.dtos.requests.UpdateTagRequest;
import com.oekrem.jwt_deneme.dtos.responses.TagResponse;
import com.oekrem.jwt_deneme.exceptions.TagExceptions.TagNameAlreadyTakenExcepiton;
import com.oekrem.jwt_deneme.models.Tag;
import com.oekrem.jwt_deneme.repositories.TagRepository;
import com.oekrem.jwt_deneme.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {


    private final TagRepository tagRepository;
    private final TagMapper tagMapper;


    @Override
    @Transactional
    public List<TagResponse> getAll() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(tagMapper::toTagResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TagResponse getTagById(UUID id) {
        return tagMapper.toTagResponse(validateTagById(id));
    }

    @Override
    @Transactional
    public TagResponse createTag(CreateTagRequest createTagRequest) {
        tagRepository.findByName(createTagRequest.getName())
                .ifPresent(tag -> {throw new TagNameAlreadyTakenExcepiton("Name already exists");});

        Tag tag = tagMapper.toTag(createTagRequest);
        Tag savedTag = tagRepository.save(tag);
        return tagMapper.toTagResponse(savedTag);
    }

    @Override
    @Transactional
    public TagResponse updateTag(UUID id, UpdateTagRequest updateTagRequest) {
        validateTagById(id);
        Tag updatedTag = tagMapper.toTag(updateTagRequest);
        updatedTag.setId(id);
        Tag tag = tagRepository.save(updatedTag);
        return tagMapper.toTagResponse(tag);
    }

    @Override
    @Transactional
    public void deleteTag(UUID id) {
        Tag tag = validateTagById(id);
        tagRepository.delete(tag);
    }

    @Override
    public Tag validateTagById(UUID id) {
        return tagRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("Tag not found with id: " + id));
    }

    @Override
    public Tag validateTagByName(String name) {
        return tagRepository.findByName(name)
                .orElseThrow( () -> new EntityNotFoundException("Tag not found with name: " + name));
    }
}
