package tw.com.mall.mapper;

import org.springframework.stereotype.Repository;
import tw.com.mall.model.ProductOrder;

import java.util.List;

@Repository
public interface ProductOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(ProductOrder record);

    int insertSelective(ProductOrder record);

    ProductOrder selectByPrimaryKey(String orderId);

    ProductOrder getOrderById(String userId, String orderId);

    List<ProductOrder> getOrderByUserId(String userId);

    int updateByPrimaryKeySelective(ProductOrder record);

    int updateByPrimaryKey(ProductOrder record);
}