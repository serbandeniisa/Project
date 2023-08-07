package siit.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import siit.model.OrderProduct;
import siit.model.Product;
import siit.sevices.ProductService;

import java.util.List;

@RestController
public class ProductController {
    //"products?term=ssda"
    @Autowired
    ProductService productService;

    @GetMapping("/api/products")
    public List<Product> getProductBy(@RequestParam("term") String term){
        return productService.getProductsBy(term);
    }


}
