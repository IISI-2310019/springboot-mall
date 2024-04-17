package tw.com.mall.mapper;

import org.springframework.stereotype.Repository;
import tw.com.mall.model.ProductOrder;

@Repository
public interface ProductOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(ProductOrder record);

    int insertSelective(ProductOrder record);

    ProductOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(ProductOrder record);

    int updateByPrimaryKey(ProductOrder record);
}