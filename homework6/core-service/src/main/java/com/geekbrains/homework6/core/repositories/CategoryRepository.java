package com.geekbrains.homework6.core.repositories;

import com.geekbrains.homework6.core.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// JpaSpecificationExecutor<Student> - поддержка спецификации

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

    Optional<Category> findByTitle(String title);


}
