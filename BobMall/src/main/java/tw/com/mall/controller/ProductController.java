package tw.com.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tw.com.mall.mapper.ProductMapper;
import tw.com.mall.model.Product;
import tw.com.mall.service.ProductService;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> selectAll(){
        return productService.selectAll();
    }

    @GetMapping("/products/{productId}")
    //public Product getProduct(@PathVariable Integer productId){
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.selectByPrimaryKey(productId);
        //return product;
        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
