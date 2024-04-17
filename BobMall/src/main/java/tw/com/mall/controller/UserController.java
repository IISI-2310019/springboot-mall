package tw.com.mall.controller;


import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import tw.com.mall.dto.UserLoginRequest;
import tw.com.mall.dto.UserRegisterRequest;
import tw.com.mall.model.User;
import tw.com.mall.service.UserService;

import java.util.List;

//要加上這個註解 @Validated，才能使用下面的 @Min @Max的功能
@Validated
@RestController
public class UserController {

    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers()
    {
        List<User> users = userService.getUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRequest)
    {
        String newUserId = java.util.UUID.randomUUID().toString();

        logger.info("UserRegisterRequest:{}", userRequest);
        logger.info("email:{}", userRequest.getEmail());
        logger.info("password:{}", userRequest.getPassword());
        logger.info("userName:{}", userRequest.getUserName());

        //檢查是否有重複的email
        if(userService.selectByEmail(userRequest.getEmail()) != null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else{
            /*
            Date now=new Date();
            User createUser = new User();
            String HashPassword = DigestUtils.md5DigestAsHex((newUserId+userRequest.getPassword()).getBytes());
            createUser.setUserId(newUserId);
            createUser.setEmail(userRequest.getEmail());
            createUser.setUserName(userRequest.getUserName());
            createUser.setPassword(HashPassword);
            createUser.setCreateDate(now);
            createUser.setLastModifyDate(now);*/


            if(userService.createUser(newUserId,userRequest) == 0)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }else{
                User NewUser = userService.selectByPrimaryKey(newUserId);
                NewUser.setPassword(null);
                return ResponseEntity.status(HttpStatus.CREATED).body(NewUser);
            }
        }
    }

    //會員登入
    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest UserLoginRequest)
    {
        logger.info("email:{}", UserLoginRequest.getEmail());
        logger.info("password:{}", UserLoginRequest.getPassword());

        //先檢查有沒有這個帳號
        User testLoginUser = userService.selectByEmail(UserLoginRequest.getEmail());

        if(testLoginUser == null)
        {
            logger.warn("無此帳號:{}!", UserLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }else{
            logger.info("有此帳號:{}!", UserLoginRequest.getEmail());
            User LoginUser = userService.getLogin(UserLoginRequest.getEmail(),UserLoginRequest.getPassword());

            if(LoginUser != null)
            {
                logger.info("登入成功:{}!", LoginUser.getEmail());
                LoginUser.setPassword(null);
                return ResponseEntity.status(HttpStatus.OK).body(LoginUser);
            }else{
                logger.info("帳號密碼不符，登入失敗:{}!", LoginUser.getEmail());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }


    }
}
