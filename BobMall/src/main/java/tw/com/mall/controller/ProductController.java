package tw.com.mall.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.mall.model.Product;
import tw.com.mall.service.ProductService;

import java.util.Date;
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
    public ResponseEntity<Product> getProduct(@PathVariable String productId){
        Product product = productService.selectByPrimaryKey(productId);
        //return product;
        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*
    * 新增商品
     */
    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody @Valid Product product)
    {
        Date now=new Date();
        if(product.getProductId() ==null || product.getProductId().isEmpty()){
            //新增
            String UUID=java.util.UUID.randomUUID().toString();
            System.out.println("now : " + now);
            product.setProductId(UUID);
            product.setCreateDate(now);
            product.setLastModifyDate(now);
            productService.insert(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        }else{
            if(productService.selectByPrimaryKey(product.getProductId()) != null)
            {
                //已經有資料
                product.setLastModifyDate(now);
                productService.updateByPrimaryKey(product);
                return ResponseEntity.status(HttpStatus.OK).body(product);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        //return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping("/products")
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid Product product)
    {
        Date now=new Date();
        if(product.getProductId() ==null || product.getProductId().isEmpty()){
            //商品ID不可空白
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else{
            if(productService.selectByPrimaryKey(product.getProductId()) != null)
            {
                //已經有資料
                product.setLastModifyDate(now);
                productService.updateByPrimaryKey(product);
                return ResponseEntity.status(HttpStatus.OK).body(product);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
    }

}
