package com.geekbrains.market.winter7.core.repositories;

import com.geekbrains.market.winter7.core.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
