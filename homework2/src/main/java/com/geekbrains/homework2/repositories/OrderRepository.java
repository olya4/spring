package com.geekbrains.homework2.repositories;





import com.geekbrains.homework2.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

// JpaSpecificationExecutor<Student> - поддержка спецификации

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

}
