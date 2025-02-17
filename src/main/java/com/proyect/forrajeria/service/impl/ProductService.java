package com.proyect.forrajeria.service.impl;

import com.proyect.forrajeria.entity.Product;
import com.proyect.forrajeria.repository.IProductRepository;
import com.proyect.forrajeria.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<Product> findAllProducts() {
        List<Product> productList = productRepository.findAll();
        return productList;
    }

    @Override
    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void updateProduct(Long id,Product lastProduct) {
        Product newProduct = productRepository.findById(id).orElse(null);
        newProduct.setProductName(lastProduct.getProductName());
        newProduct.setProductPrice(lastProduct.getProductPrice());
        newProduct.setProductProtein(lastProduct.getProductProtein());
        newProduct.setProductWeight(lastProduct.getProductWeight());
        newProduct.setProductFlavor(lastProduct.getProductFlavor());

        productRepository.save(newProduct);
    }
}
