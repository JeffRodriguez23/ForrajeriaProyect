package com.proyect.forrajeria.controller;

import com.proyect.forrajeria.configuration.ModelMapperConfig;
import com.proyect.forrajeria.dto.ProductCustomDTO;
import com.proyect.forrajeria.dto.ProductDTO;
import com.proyect.forrajeria.entity.Maker;
import com.proyect.forrajeria.entity.Product;
import com.proyect.forrajeria.service.IMakerService;
import com.proyect.forrajeria.service.IProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/forrajeria/product")
public class ProductController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IProductService productService;
    @Autowired
    private IMakerService makerService;


    @GetMapping("/find/{id}")
    public ResponseEntity<ProductCustomDTO> findProductById(@PathVariable Long id) {

        Optional<Product> productOptional = productService.findProductById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductCustomDTO productCustomDTO = modelMapper.map(product, ProductCustomDTO.class);
            return ResponseEntity.ok(productCustomDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAllProducts() {

        List<ProductCustomDTO> productCustomDTOList = productService.findAllProducts().stream()
                .map(product -> {
                    TypeMap<Product, ProductCustomDTO> productCustomList = modelMapper
                            .typeMap(Product.class, ProductCustomDTO.class)
                            .addMappings(mapper -> {
                                mapper.map(src -> src.getMaker().getMakerName(), ProductCustomDTO::setMakerName);
                            });
                    ProductCustomDTO productCustomDTO = modelMapper.map(product, ProductCustomDTO.class);
                    return productCustomDTO;
                }).collect(Collectors.toList());


        return ResponseEntity.ok(productCustomDTOList);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) throws URISyntaxException {
        if (productDTO.getProductName().isBlank() || productDTO.getProductPrice() == null
                || productDTO.getProductProtein().isBlank()
                || productDTO.getProductWeight().isBlank()
                || productDTO.getProductFlavor().isBlank()
                || productDTO.getProductSize().isBlank()
                || productDTO.getMakerId() == null) {

            return ResponseEntity.badRequest().build();
        } else {
            Product product = modelMapper.map(productDTO, Product.class);
            Optional<Maker> maker = makerService.findMakerById(productDTO.getMakerId());

            product.setId(null); // Forzamos a que sea un nuevo producto
            product.setMaker(maker.get()); // Relacionamos el Maker correcto
            productService.saveProduct(product);

        }
        return ResponseEntity.created(new URI("/forrajeria/product/save")).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        if (id != null) {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Registro Eliminado");
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Optional<Product> productOptional = productService.findProductById(id);
        if (productOptional.isPresent()) {
            Product newProduct = productOptional.get();
            newProduct.setProductName(productDTO.getProductName());
            newProduct.setProductPrice(productDTO.getProductPrice());
            newProduct.setProductProtein(productDTO.getProductProtein());
            newProduct.setProductWeight(productDTO.getProductWeight());
            newProduct.setProductFlavor(productDTO.getProductFlavor());
            newProduct.setProductSize(productDTO.getProductSize());
            Optional<Maker> makerOptional = makerService.findMakerById(productDTO.getMakerId());
            if (makerOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Maker no encontrado");
            }
            newProduct.setMaker(makerOptional.get());

            productService.saveProduct(newProduct);

            return ResponseEntity.ok("Registro Actualizado");
        }
        return ResponseEntity.notFound().build();
    }
}
