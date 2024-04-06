package tw.com.mall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.com.mall.mapper.ProductMapper;
import tw.com.mall.model.Product;

import java.util.Date;
import java.util.List;

@Component
public class ProductService implements ProductMapper {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public int deleteByPrimaryKey(String productId) {
        return 0;
    }

    @Override
    public int insert(Product row) {
        int result=0;
        try {
            result = productMapper.insert(row);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int insertSelective(Product row) {
        return 0;
    }

    @Override
    public Product selectByPrimaryKey(String productId) {
        return productMapper.selectByPrimaryKey(productId);
    }

    @Override
    public List<Product> selectAll() {
        return productMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKeySelective(Product row) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Product row) {
        return 0;
    }
}
