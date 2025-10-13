package com.app.ecom.services;

import com.app.ecom.dto.ProductDTO;
import com.app.ecom.models.Product;
import com.app.ecom.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public void createProduct(ProductDTO product){
        productRepository.save(modelMapper.map(product, Product.class));
    }

    public List<ProductDTO> getAllProducts(){
        return productRepository.findAll().stream().map(product -> modelMapper.map(product, ProductDTO.class)).collect(Collectors.toList());
    }


}
