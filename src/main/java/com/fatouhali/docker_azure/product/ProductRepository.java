package com.fatouhali.docker_azure.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT  p FROM Product p WHERE  p.title like:rq")
    public Page<Product> searchEmployerLastName(@Param("rq") String rq, Pageable pageable);
}
