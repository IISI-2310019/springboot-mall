package tw.com.mall.dto;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import java.util.List;


public class CreateOrderRequest {

    // 購買商品的清單
    //@NotEmpty 是用在Map 和 List 上的，用來檢查集合是否為null或是size為0
    @NotEmpty
    private List<BuyItem> buyItemList;

    public List<BuyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(List<BuyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }
}
