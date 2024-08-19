package com.fatouhali.docker_azure.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

Product saveProduct(Product product);

List<Product> getAllProduct();

Page<Product> findTitleProduct(String query, Pageable page);
}
