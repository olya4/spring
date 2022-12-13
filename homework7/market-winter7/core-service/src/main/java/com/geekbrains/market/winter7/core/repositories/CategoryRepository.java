package com.geekbrains.market.winter7.core.repositories;

import com.geekbrains.market.winter7.core.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

    Optional<Category> findByTitle(String title);


}
