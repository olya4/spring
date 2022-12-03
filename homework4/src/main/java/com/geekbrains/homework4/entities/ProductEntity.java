package com.geekbrains.homework4.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Integer price;

    // hibernate сам заполнит поле, при создании сущности
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // hibernate сам заполнит поле, при изменении сущности
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}


