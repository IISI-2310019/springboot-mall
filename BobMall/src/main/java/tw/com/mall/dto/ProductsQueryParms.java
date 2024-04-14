package tw.com.mall.dto;

import org.springframework.stereotype.Component;
import tw.com.mall.constant.ProductCategory;

@Component
public class ProductsQueryParms {
    private ProductCategory category;
    private String keyword;

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
