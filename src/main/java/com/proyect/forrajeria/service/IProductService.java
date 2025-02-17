package com.proyect.forrajeria.service;

import com.proyect.forrajeria.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IProductService {

    public List<Product> findAllProducts();

    public Optional<Product> findProductById(Long id);

    public void saveProduct(Product product);

    public void deleteProduct(Long id);

    public void updateProduct(Long id,Product lastProduct);

}
