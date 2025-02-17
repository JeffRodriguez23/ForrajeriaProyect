package com.proyect.forrajeria.controller;

import com.proyect.forrajeria.entity.Product;
import com.proyect.forrajeria.service.IMakerService;
import com.proyect.forrajeria.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/forrajeria/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findProductById(@PathVariable Long id) {

        Optional<Product> productOptional = productService.findProductById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAllProducts() {
        List<Product> productList = productService.findAllProducts();
        if (productList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productList);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveProduct(@RequestBody Product product) throws URISyntaxException {
        if (product.getProductName().isBlank() || product.getProductPrice() == null || product.getProductProtein().isBlank() || product.getProductWeight().isBlank() || product.getProductFlavor().isBlank() || product.getMaker() == null) {
            return ResponseEntity.badRequest().build();
        } else {
            productService.saveProduct(product);
        }
        return ResponseEntity.created(new URI("/forrajeria/product/save")).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        if (id != null) {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Registro Eliminado");
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> productOptional = productService.findProductById(id);
        if (productOptional.isPresent()) {
            Product newProduct = productOptional.get();
            newProduct.setProductName(product.getProductName());
            newProduct.setProductPrice(product.getProductPrice());
            newProduct.setProductProtein(product.getProductProtein());
            newProduct.setProductWeight(product.getProductWeight());
            newProduct.setProductFlavor(product.getProductFlavor());
            newProduct.setMaker(product.getMaker());

            productService.saveProduct(newProduct);

            return ResponseEntity.ok("Registro Actualizado");
        }
        return ResponseEntity.notFound().build();
    }
}
