package tw.com.mall.mapper;

import org.springframework.stereotype.Repository;
import tw.com.mall.model.OrderItem;

@Repository
public interface OrderItemMapper {
    int deleteByPrimaryKey(String orderItemId);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(String orderItemId);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
}