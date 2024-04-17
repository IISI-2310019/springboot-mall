package tw.com.mall.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import tw.com.mall.dto.BuyItem;
import tw.com.mall.dto.CreateOrderRequest;
import tw.com.mall.mapper.ProductMapper;
import tw.com.mall.mapper.ProductOrderMapper;
import tw.com.mall.model.OrderItem;
import tw.com.mall.model.Product;
import tw.com.mall.model.ProductOrder;
import tw.com.mall.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private UserService userService;

    @Override
    public int deleteByPrimaryKey(String orderId) {
        return 0;
    }


    public String createOrder(String userId, CreateOrderRequest createOrderRequest)
    {
        //檢查會員是否存在，如果不存在就回傳錯誤
        User user = userService.selectByPrimaryKey(userId);
        if(user == null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //紀錄可購買的產品
        List<OrderItem> orderItems = new ArrayList<>();

        String OrderId = java.util.UUID.randomUUID().toString();
        Date now = new Date();
        //要記錄主訂單的資訊(訂單ID 會員ID 總金額) 並寫入到訂單明細 order_item當中

        //1.計算總金額
        int totalAmount = 0;
        for(BuyItem buyItem : createOrderRequest.getBuyItemList())
        {
            logger.info("getProductId:{}",buyItem.getProductId());
            Product product = productMapper.selectByPrimaryKey(buyItem.getProductId());
            if(product == null)
            {
                logger.warn("商品:{} 不存在",buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }else if(buyItem.getQuantity() > product.getStock()){
                logger.warn("商品:{}，庫存量不足，無法購買。剩餘庫存{}，欲購買數量{}",
                        product.getProductId(),
                        product.getStock(),
                        buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            //扣除產品庫存
            logger.info("產品 ID:{}，欲購買數量:{}，剩餘數量:{}",product.getProductId(),buyItem.getQuantity(),product.getStock()-buyItem.getQuantity());
            productMapper.updateProductStock(product.getProductId(),product.getStock()-buyItem.getQuantity());

            //計算總價錢
            int amt = product.getPrice() * buyItem.getQuantity();
            logger.info("amt:{}",amt);
            totalAmount += amt;

            //轉成OrderItem物件

            OrderItem orderItem = new OrderItem();
            orderItem.setOrderItemId(java.util.UUID.randomUUID().toString());
            orderItem.setOrderId(OrderId);
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amt);
            orderItems.add(orderItem);
        }
        logger.info("totalAmount:{}",totalAmount);
        //2.建立訂單資料
        ProductOrder productOrder = new ProductOrder();
        productOrder.setOrderId(OrderId);
        productOrder.setUserId(userId);
        productOrder.setTotalAmount(totalAmount);
        productOrder.setCreatedDate(now);
        productOrder.setLastModifiedDate(now);
        //寫入主訂單
        int createOrder = productOrderMapper.insert(productOrder);
        logger.info("createOrder:{}",createOrder);

        if(createOrder == 1)
        {
            //3.回傳主訂單編號，透過OrderItemService建立訂單明細
            orderItemService.CreateOrderItems(userId,OrderId,orderItems);
            //orderItemService.CreateOrderItem(userId,OrderId,createOrderRequest);

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
    public ProductOrder getOrderById(String userId,String orderId) {
        return productOrderMapper.getOrderById(userId,orderId);
    }

    @Override
    public List<ProductOrder> getOrderByUserId(String userId) {
        return productOrderMapper.getOrderByUserId(userId);
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
