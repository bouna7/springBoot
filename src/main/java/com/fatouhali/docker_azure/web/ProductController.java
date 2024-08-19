package com.fatouhali.docker_azure.web;


import com.fatouhali.docker_azure.product.Product;
import com.fatouhali.docker_azure.product.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

    private  final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "")
    public Product saveProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }

    @GetMapping(path = "")
    public List<Product> getAllProduct(){
        return productService.getAllProduct();
    }


    @GetMapping(path = "search")
    public Page<Product> findTitleProduct(@RequestParam(name = "rq")String request, @RequestParam(name = "page",defaultValue = "0")int page,
                                          @RequestParam(name = "size", defaultValue = "7")int size){
        return productService.findTitleProduct("%"+request+"%",PageRequest.of(page,size));
    }
}
