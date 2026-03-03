package com.example.shopmyway.Controller;

import com.example.shopmyway.Services.ProductService;
import com.example.shopmyway.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Port;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ShopMyWay")
public class ProductController {

   private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // post new product
    @PostMapping(path = "/products", consumes = {"application/json"})
    public Product sendProduct(@RequestBody Product product){
        return service.addProducts(product);
    }

    @GetMapping(path="products", produces = {"application/json"})
    public List<Product> products(){
        return service.getProducts();
    }

    @GetMapping(path = "/product/{id}")
    public Optional<Product> productsById(@PathVariable("id") int productId){
        return service.getProduct(productId);

    }

    @DeleteMapping(path = "/product/{id}")
    public String productDelete(@PathVariable("id") int productId){
        return service.deleteProduct(productId);

    }

    @PutMapping(path = "/product/{id}")
    public Product productUpdate(@PathVariable("id") int productId, @RequestBody Product updateProduct){
        return service.updateProducts(productId,updateProduct);
    }


}
