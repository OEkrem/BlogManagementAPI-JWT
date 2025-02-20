package com.oekrem.jwt_deneme.repositories;

import com.oekrem.jwt_deneme.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

    @Query("select u from Tag u where u.name = :name")
    Optional<Tag> findByName(String name);

}
