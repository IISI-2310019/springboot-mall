package tw.com.mall.mapper;

import org.springframework.stereotype.Repository;
import tw.com.mall.model.User;

import java.util.List;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(User user);

    int insertSelective(User record);

    User selectByPrimaryKey(String userId);

    List<User> getUsers();

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByEmail(String email);

    User getLogin(String email,String password);
}