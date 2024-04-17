package tw.com.mall.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tw.com.mall.controller.UserController;
import tw.com.mall.dto.BuyItem;
import tw.com.mall.dto.CreateOrderRequest;
import tw.com.mall.mapper.ProductMapper;
import tw.com.mall.mapper.ProductOrderMapper;
import tw.com.mall.model.Product;
import tw.com.mall.model.ProductOrder;

import java.util.Date;

@Transactional
@Component
public class ProductOrderService implements ProductOrderMapper {

    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(ProductOrderService.class);

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Autowired
    private OrderItemService orderItemService;

    @Override
    public int deleteByPrimaryKey(String orderId) {
        return 0;
    }


    public String createOrder(String userId, CreateOrderRequest createOrderRequest)
    {
        String OrderId = java.util.UUID.randomUUID().toString();
        Date now = new Date();
        //要記錄主訂單的資訊(訂單ID 會員ID 總金額) 並寫入到訂單明細 order_item當中

        //1.計算總金額
        Long totalAmount = 0L;
        for(BuyItem buyItem : createOrderRequest.getBuyItemList())
        {
            logger.info("getProductId:{}",buyItem.getProductId());
            Product product = productMapper.selectByPrimaryKey(buyItem.getProductId());
            int amt = product.getPrice() * buyItem.getQuantity();
            logger.info("amt:{}",amt);
            totalAmount += amt;
            //productOrderMapper.insertOrderItem(OrderId,buyItem.getProductId(),buyItem.getQuantity(),buyItem.getAmount());
        }
        logger.info("totalAmount:{}",totalAmount);
        //2.建立訂單資料
        ProductOrder productOrder = new ProductOrder();
        productOrder.setOrderId(OrderId);
        productOrder.setUserId(userId);
        productOrder.setTotalAmount(totalAmount.intValue());
        productOrder.setCreatedDate(now);
        productOrder.setLastModifiedDate(now);

        int createOrder = productOrderMapper.insert(productOrder);
        logger.info("createOrder:{}",createOrder);

        if(createOrder == 1)
        {
            //3.回傳主訂單編號，透過OrderItemService建立訂單明細
            orderItemService.CreateOrderItem(userId,OrderId,createOrderRequest);

            return OrderId;
        }else{
            return null;
        }
    }


    @Override
    public int insert(ProductOrder record) {
        return productOrderMapper.insert(record);
    }

    @Override
    public int insertSelective(ProductOrder record) {
        return 0;
    }

    @Override
    public ProductOrder selectByPrimaryKey(String orderId) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(ProductOrder record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(ProductOrder record) {
        return 0;
    }
}
