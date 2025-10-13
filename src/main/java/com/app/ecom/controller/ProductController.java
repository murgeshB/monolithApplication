package com.app.ecom.controller;

import com.app.ecom.dto.ProductDTO;
import com.app.ecom.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

   public ResponseEntity<String> addProduct(ProductDTO productDTO) {
        productService.createProduct(productDTO);
        return ResponseEntity.ok("Product added successfully");
   }

}
