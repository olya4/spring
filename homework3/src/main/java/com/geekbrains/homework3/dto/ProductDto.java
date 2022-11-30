package com.geekbrains.homework3.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// объекты для передачи и получения данных из вне
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    // прописываются только те поля, которые требуется передать клиенту

    private Long id;

    private String title;

    private Integer price;

}
