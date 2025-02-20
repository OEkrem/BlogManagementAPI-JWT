package com.oekrem.jwt_deneme.dtos.requests;

import com.oekrem.jwt_deneme.models.enums.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePostRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Post status is required")
    private PostStatus postStatus;

    @NotNull(message = "User is required")
    private UUID user_id;

    @NotNull(message = "Tag is required")
    @Size(min = 1, max = 10, message = "Tag must be between {min} and {max} ")
    private Set<UUID> tagIds;

    @NotNull(message = "Category is required")
    private UUID category_id;
}
