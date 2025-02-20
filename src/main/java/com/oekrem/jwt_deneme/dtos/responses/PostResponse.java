package com.oekrem.jwt_deneme.dtos.responses;


import com.oekrem.jwt_deneme.models.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {

    private UUID id;
    private String title;
    private String content;

    private Integer readingTime;

    private PostStatus postStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UUID userId;

    private Set<UUID> tagsIds;

    private UUID categoryId;
}
