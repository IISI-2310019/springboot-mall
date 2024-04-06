package tw.com.mall.model;

import tw.com.mall.constant.ProductCategory;

import java.util.Date;

public class Product {
    private Integer productId;

    private String productName;

    //private String category  > 改寫為 Enum 類型
    private ProductCategory category;

    private String imageUrl;

    private Integer price;

    private Integer stock;

    private String description;

    private Date createDate;

    private Date lastModifyDate;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(String category) {
        //String categoryName = category == null ? null : category.trim();
        //ProductCategory Category = ProductCategory.valueOf(categoryName);
        this.category = ProductCategory.valueOf(category == null ? null : category.trim());
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }
}