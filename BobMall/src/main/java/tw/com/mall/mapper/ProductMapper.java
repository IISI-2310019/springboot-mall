package tw.com.mall.mapper;

import tw.com.mall.model.Product;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer productId);

    int insert(Product row);

    int insertSelective(Product row);

    List<Product> selectAll();
    Product selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(Product row);

    int updateByPrimaryKey(Product row);
}