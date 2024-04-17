package tw.com.mall.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.mall.dto.CreateOrderRequest;
import tw.com.mall.model.ProductOrder;
import tw.com.mall.service.OrderItemService;
import tw.com.mall.service.ProductOrderService;

@RestController
public class OrderController {

    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private ProductOrderService productOrderService;


    @Autowired
    private OrderItemService orderItemService;

    /*
    * 取得所有訂單
    * @param userId
    */
    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<?> getOrders(@PathVariable @NotNull String userId){
        try{
            return ResponseEntity.ok(productOrderService.getOrderByUserId(userId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }



    /*
    *   建立訂單
    */
    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable String userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest){
        try{
            //1.建立訂單
            String OrderId = productOrderService.createOrder(userId,createOrderRequest);

            logger.info("OrderIdByController1:{}",OrderId);

            if(OrderId == null)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }else {

                ProductOrder productOrder = productOrderService.getOrderById(userId,OrderId);

                return ResponseEntity.status(HttpStatus.CREATED).body(productOrder);
                /*
                //2.建立訂單明細
                logger.info("OrderIdByController2:{}",OrderId);

                int ItemCount = orderItemService.CreateOrderItem(userId,OrderId,createOrderRequest);

                logger.info("ItemCount:{}",ItemCount);

                if(ItemCount == createOrderRequest.getBuyItemList().size())
                {
                    return ResponseEntity.status(HttpStatus.CREATED).body(OrderId);
                }else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }*/
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
