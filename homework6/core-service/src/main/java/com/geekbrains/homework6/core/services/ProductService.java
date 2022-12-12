package com.geekbrains.homework6.core.services;

import com.geekbrains.homework6.api.ProductDto;
import com.geekbrains.homework6.api.ResourceNotFoundException;
import com.geekbrains.homework6.core.entities.Product;
import com.geekbrains.homework6.core.repositories.ProductRepository;
import com.geekbrains.homework6.core.repositories.specifications.ProductsSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
// для увязки бинов ProductService и ProductRepository
@RequiredArgsConstructor
public class ProductService {
    // пометить final все поля, кот. надо увязать с бином ProductService
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    // возвращается Product, т.к. после сохранения бд присвоит ему id
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    // чтобы весь метод выполнялся в рамках одной транзакции
    public Product update(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Невозможно обновить продукт, не найден в базе id: " + productDto.getId()));
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        // save делать не надо, т.к. это транзакционный метод и hibernate сам сохранит обновленный продукт
        return product;
    }

    @Transactional
    // чтобы весь метод выполнялся в рамках одной транзакции
    public Product createNewProduct(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() ->
                new ResourceNotFoundException("Category not found")));
        productRepository.save(product);
        return product;
    }

    // всевозможные вариации поиска и сортировки сводятся к одному методу
    // в объекте Page хранится список объектов, номер текущей страницы, всего страниц

    // Integer minPrice, Integer maxPrice, String partTitle - аргументы для методов ProductsSpecifications
    public Page<Product> find(BigDecimal minPrice, BigDecimal maxPrice, String partTitle, Integer page) {

        // собрать спецификацию (все методы из ProductsSpecifications собираются в один общий)
        Specification<Product> specification = Specification.where(null);
        // select p from Product p where true - вывести всех

        // если есть minPrice
        if (minPrice != null) {
            // добавить к запросу
            specification = specification.and(ProductsSpecifications.priceGreaterOrEqualsThan(minPrice));
            // select p from Product p where true AND p.price > minPrice
        }
        // если есть maxPrice
        if (maxPrice != null) {
            // добавить к запросу
            specification = specification.and(ProductsSpecifications.priceLessThanOrEqualsThan(maxPrice));
            // select p from Product p where true AND p.price > minPrice AND p.price < maxPrice
        }
        // если есть partTitle
        if (partTitle != null) {
            // добавить к запросу
            specification = specification.and(ProductsSpecifications.titleLike(partTitle));
            // select p from Product p where true AND p.price > minPrice AND p.price < maxPrice AND p.title like '%partTitle%'
        }
        // specification - набор критериев для поисков объектов в БД
        // specification = select s from Student s where true AND s.score > minScore AND s.score < maxScore AND s.name like '%partName%'

        // PageRequest.of(page -1, 5) - выводить результат с 1 страницы, т.к. индексация страниц начинается с 0, по 5 продуктов
        // в пагинацию можно добавить сортировку по конкретному столбу
        // PageRequest.of(page -1, 5, Sort.by(столбец))
        return productRepository.findAll(specification, PageRequest.of(page - 1, 5));
    }

}
