package tw.com.mall.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tw.com.mall.dto.BuyItem;
import tw.com.mall.dto.CreateOrderRequest;
import tw.com.mall.mapper.OrderItemMapper;
import tw.com.mall.mapper.ProductMapper;
import tw.com.mall.model.OrderItem;
import tw.com.mall.model.Product;

import java.util.List;

@Transactional
@Component
public class OrderItemService implements OrderItemMapper{

    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(OrderItemService.class);

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public int deleteByPrimaryKey(String orderItemId) {
        return 0;
    }

    //處理新增訂單明細(考慮庫存之後可購買的)
    public int CreateOrderItems(String userId,String OrderId, List<OrderItem> orderItems)
    {
        int ItemCount = 0;
        for(OrderItem orderItem : orderItems)
        {
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());

            int amt = product.getPrice() * orderItem.getQuantity();
            orderItem.setAmount(amt);
            orderItem.setOrderId(OrderId);
            orderItem.setOrderItemId(java.util.UUID.randomUUID().toString());
            if(orderItemMapper.insert(orderItem)>0)
            {
                ItemCount++;
            }
        }
        return ItemCount;
    }

    /*
    //處理新增訂單明細
    public int CreateOrderItem(String userId,String OrderId, CreateOrderRequest createOrderRequest)
    {
        int ItemCount = 0;
        for(BuyItem buyItem : createOrderRequest.getBuyItemList())
        {
            String OrderItemId = java.util.UUID.randomUUID().toString();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderItemId(OrderItemId);
            orderItem.setOrderId(OrderId);
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            Product product = productMapper.selectByPrimaryKey(buyItem.getProductId());
            int amt = product.getPrice() * buyItem.getQuantity();
            orderItem.setAmount(amt);

            logger.info("OrderItemId:{}",OrderItemId);
            logger.info("OrderId:{}",OrderId);
            logger.info("ProductId:{}",buyItem.getProductId());
            logger.info("Quantity:{}",buyItem.getQuantity());
            logger.info("Amount:{}",amt);

            //新增訂單明細
            if(orderItemMapper.insert(orderItem)>0)
            {
                ItemCount++;
            }
        }
        logger.info("ItemCount:{}",ItemCount);
        return ItemCount;
    }*/

    @Override
    public int insert(OrderItem record) {
        return orderItemMapper.insert(record);
    }

    @Override
    public int insertSelective(OrderItem record) {
        return 0;
    }

    @Override
    public OrderItem selectByPrimaryKey(String orderItemId) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(OrderItem record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(OrderItem record) {
        return 0;
    }
}
