package tw.com.mall.mapper;

import org.apache.ibatis.annotations.Param;
import tw.com.mall.dto.ProductsQueryParms;
import tw.com.mall.model.Product;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(String productId);

    int insert(Product row);

    int insertSelective(Product row);

    List<Product> selectAll();

    //指定參數 @Param("QueryParms") 用來將 ProductsQueryParms 物件傳入
    //指定參數 @Param("Page") 用來將 page 傳入
    List<Product> getProducts(@Param("QueryParms") ProductsQueryParms productsQueryParms,@Param("Page") Integer page);
    //List<Product> getProducts(ProductCategory category, String keyword, Integer page);

    Product selectByPrimaryKey(String productId);

    int updateByPrimaryKeySelective(Product row);

    int updateByPrimaryKey(String productId,Product row);

    void updateProduct(String productId,Product row);
}