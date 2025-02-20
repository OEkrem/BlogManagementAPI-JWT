package com.oekrem.jwt_deneme.repositories;

import com.oekrem.jwt_deneme.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Query("select c from Category c where c.name = :name")
    Optional<Category> findByName(String name);
}
