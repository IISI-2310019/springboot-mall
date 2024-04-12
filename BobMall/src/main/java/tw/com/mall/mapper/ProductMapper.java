package tw.com.mall.mapper;

import tw.com.mall.model.Product;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(String productId);

    int insert(Product row);

    int insertSelective(Product row);

    List<Product> selectAll();

    Product selectByPrimaryKey(String productId);

    int updateByPrimaryKeySelective(Product row);

    int updateByPrimaryKey(String productId,Product row);

    void updateProduct(String productId,Product row);
}