package tw.com.mall.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import tw.com.mall.controller.UserController;
import tw.com.mall.dto.UserRegisterRequest;
import tw.com.mall.mapper.UserMapper;
import tw.com.mall.model.User;

import java.util.Date;
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

    /*
    * 創建新用戶
    * @param newUserId
    * @param userRegisterRequest
     */
    public int createUser(String newUserId,UserRegisterRequest userRegisterRequest) {

        //檢查是否有重複的email
        if(userMapper.selectByEmail(userRegisterRequest.getEmail()) != null)
        {
            return 0;
        }else{
            Date now=new Date();
            User createUser = new User();
            String HashPassword = DigestUtils.md5DigestAsHex((newUserId+userRegisterRequest.getPassword()).getBytes());
            createUser.setUserId(newUserId);
            createUser.setEmail(userRegisterRequest.getEmail());
            createUser.setPassword(HashPassword);
            createUser.setUserName(userRegisterRequest.getUserName());
            createUser.setCreateDate(now);
            createUser.setLastModifyDate(now);
            return userMapper.insert(createUser);
        }
    }

    @Override
    public int insert(User createUser) {
        return userMapper.insert(createUser);
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
            return queryUser;
        }else{
            logger.info("Email:{} is available", email);
            return null;
        }
    }

    @Override
    public User getLogin(String email,String password)
    {
        User requestUser = userMapper.selectByEmail(email);
        if(requestUser != null)
        {
            String HashPassword = DigestUtils.md5DigestAsHex((requestUser.getUserId()+password).getBytes());
            if(requestUser.getPassword().equals(HashPassword)) {
                logger.info("Login Success");
                return requestUser;
            }else{
                logger.warn("Login Data is Error");
                return null;
            }
        }else{
            logger.warn("Login Data is Error");
            return null;
        }

        /*
        User LoginUser = userMapper.getLogin(email,HashPassword);
        if(LoginUser == null) {
            logger.warn("Login Data is Error");
            return null;
        }else{
            logger.info("Login Success");
            return LoginUser;
        }
        */
    }
}
