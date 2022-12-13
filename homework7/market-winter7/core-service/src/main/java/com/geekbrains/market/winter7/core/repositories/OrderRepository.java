package com.geekbrains.market.winter7.core.repositories;

import com.geekbrains.market.winter7.core.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
