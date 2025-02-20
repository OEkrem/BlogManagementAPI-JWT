package com.oekrem.jwt_deneme.repositories;

import com.oekrem.jwt_deneme.models.Category;
import com.oekrem.jwt_deneme.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    @Query("select p from Post p where p.postStatus= 'PUBLISHED' ")
    List<Post> findAllPublished();

    @Query("""
            select p from Post p where 
                        p.category.id = :categoryId and 
                        exists(select t.id from p.tags t where t.id = :tagId) and
                        p.postStatus = "PUBLISHED"
            """)
    List<Post> findAllPublishedByCategoryAndTagId(UUID categoryId, UUID tagId);

    @Query("select t from Post t where t.category.id = :categoryId and t.postStatus = 'PUBLISHED'")
    List<Post> findAllPublishedByCategory(UUID categoryId);

    @Query("select p from Post p where exists(select t.id from p.tags t where t.id = :tagId) and p.postStatus = 'PUBLISHED'")
    List<Post> findAllPublishedByTag(UUID tagId);

    @Query("select p from Post p where p.user.id = :userId and p.postStatus='DRAFTED' ")
    List<Post> findAllDraftsByUserId(UUID userId);
}
