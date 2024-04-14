package tw.com.mall.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.mall.constant.ProductCategory;
import tw.com.mall.dto.ProductsQueryParms;
import tw.com.mall.model.Product;
import tw.com.mall.service.ProductService;

import java.util.Date;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProduct(
                        //查詢條件Filter
                        @RequestParam(required = false) ProductCategory category,
                        @RequestParam(required = false) String keyword,
                        //排序
                        @RequestParam(required = false,defaultValue = "create_date") String OrderBy,
                        @RequestParam(required = false,defaultValue = "desc") String Sort,
                        //單頁筆數
                        @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                        //分頁
                        @RequestParam(required = false) Integer page
            ) {

        ProductsQueryParms productsQueryParms = new ProductsQueryParms();

        productsQueryParms.setCategory(category);
        productsQueryParms.setKeyword(keyword);
        productsQueryParms.setOrderBy(OrderBy);
        productsQueryParms.setSort(Sort);
        productsQueryParms.setPage(page);
        productsQueryParms.setPageSize(pageSize);

        //List<Product> Products =  productService.getProducts(category,keyword,page);
        List<Product> Products =  productService.getProducts(productsQueryParms);

        //回傳HttpStatusCode 200
        return ResponseEntity.status(HttpStatus.OK).body(Products);
    }


    //@GetMapping("/products")
    //public List<Product> selectAll(){
    //    return productService.selectAll();
    //}

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
            product.setProductId(UUID);
            product.setCreateDate(now);
            product.setLastModifyDate(now);
            productService.insert(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            /*
            if(productService.selectByPrimaryKey(product.getProductId()) != null)
            {
                //已經有資料
                product.setLastModifyDate(now);
                productService.updateByPrimaryKey(product);
                return ResponseEntity.status(HttpStatus.OK).body(product);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
             */
        }
        //return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(
                                            @PathVariable String productId,
                                            @RequestBody @Valid Product product)
    {
        Date now=new Date();
        if( productId ==null || productId.isEmpty() || productId.isBlank() || !product.getProductId().equals(productId) ){
            //商品ID不可空白
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else{
            Product product1 = productService.selectByPrimaryKey(productId);
            if(product1 != null)
            {
                //已經有資料
                product.setLastModifyDate(now);
                System.out.println("now : " + now);
                //
                productService.updateProduct(productId,product);
                //為了要回傳顯示建立時間
                product.setCreateDate(product1.getCreateDate());

                return ResponseEntity.status(HttpStatus.OK).body(product);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
    }

    //刪除
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable String productId){
        Date now=new Date();
        if( productId == null || productId.isEmpty() || productId.isBlank()){
            //商品ID不可空白
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else{
            productService.deleteByPrimaryKey(productId);
            //回傳HttpStatus 204 不需要強調是否刪除成功
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }



}
