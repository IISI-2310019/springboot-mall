package tw.com.mall.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tw.com.mall.dto.OrderQueryParms;
import tw.com.mall.dto.ProductsQueryParms;
import tw.com.mall.model.Product;
import tw.com.mall.model.ProductOrder;

import java.util.List;

@Repository
public interface ProductOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(ProductOrder record);

    int insertSelective(ProductOrder record);

    List<ProductOrder> selectAll();

    //指定參數 @Param("QueryParms") 用來將 OrderQueryParms 物件傳入
    //指定參數 @Param("Page") 用來將 page 傳入
    List<ProductOrder> getOrders(@Param("QueryParms") OrderQueryParms orderQueryParms);

    ProductOrder selectByPrimaryKey(String orderId);

    ProductOrder getOrderById(String userId, String orderId);

    List<ProductOrder> getOrderByUserId(String userId);

    int updateByPrimaryKeySelective(ProductOrder record);

    int updateByPrimaryKey(ProductOrder record);
}