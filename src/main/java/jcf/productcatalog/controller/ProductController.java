package jcf.productcatalog.controller;

import jcf.productcatalog.model.Product;
import jcf.productcatalog.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  @Autowired
  private ProductRepository productRepository;

  @GetMapping
  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable Long id) {
   Optional<Product> product = productRepository.findById(id);

    if(product.isPresent()) {
      return ResponseEntity.ok(product.get());
    }else{
      return ResponseEntity.notFound().build();
    }

  }

  @PostMapping
  public ResponseEntity<Product> createProduct(@RequestBody Product product) {
    return ResponseEntity.ok(productRepository.save(product));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
    Optional<Product> product = productRepository.findById(id);

    if(product.isPresent()) {
      Product existingProduct = product.get();
      existingProduct.setName(productDetails.getName());
      existingProduct.setDescription(productDetails.getDescription());
      existingProduct.setPrice(productDetails.getPrice());

      return ResponseEntity.ok(productRepository.save(existingProduct));
    }else{
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
    Optional<Product> product = productRepository.findById(id);

    if(product.isPresent()) {
      productRepository.delete(product.get());
      return ResponseEntity.ok().build();
    }else{
      return ResponseEntity.notFound().build();
    }
  }
}
