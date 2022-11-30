package com.geekbrains.homework3.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // при добавлении или удалении заказа - список товаров будет тоже сохранен или удален
    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<OrderItem> items;

    @Column(name = "total_price")
    private Integer totalPrice;

    @Column(name = "address")
    private String address;

    @Column(name = "telephone")
    private String telephone;

    // hibernate сам заполнит поле, при создании сущности
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // hibernate сам заполнит поле, при изменении сущности
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}


