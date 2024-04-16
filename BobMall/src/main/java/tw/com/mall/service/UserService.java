package tw.com.mall.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.com.mall.controller.UserController;
import tw.com.mall.mapper.UserMapper;
import tw.com.mall.model.User;

import java.util.List;

@Component
public class UserService implements UserMapper {

    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUsers() {
        return userMapper.getUsers();
    }

    @Override
    public int deleteByPrimaryKey(String userId) {
        return 0;
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int insertSelective(User record) {
        return 0;
    }

    @Override
    public User selectByPrimaryKey(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return 0;
    }

    @Override
    public User selectByEmail(String email)
    {
        User queryUser = userMapper.selectByEmail(email);

        if(queryUser != null) {
            logger.warn("Email:{} is already exist", email);
            return null;
        }else{
            logger.info("Email:{} is available", email);
            return queryUser;
        }
    }
}
