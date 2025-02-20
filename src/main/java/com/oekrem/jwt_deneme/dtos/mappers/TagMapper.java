package com.oekrem.jwt_deneme.dtos.mappers;

import com.oekrem.jwt_deneme.dtos.requests.CreateTagRequest;
import com.oekrem.jwt_deneme.dtos.requests.UpdateTagRequest;
import com.oekrem.jwt_deneme.dtos.responses.TagResponse;
import com.oekrem.jwt_deneme.models.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag toTag(CreateTagRequest createTagRequest);
    Tag toTag(UpdateTagRequest updateTagRequest);
    TagResponse toTagResponse(Tag tag);

}
