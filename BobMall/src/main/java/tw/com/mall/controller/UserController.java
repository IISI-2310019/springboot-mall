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
import tw.com.mall.model.User;
import tw.com.mall.service.UserService;

import java.util.Date;
import java.util.List;

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
    public ResponseEntity<User> register(@RequestBody @Valid User userRequest)
    {
        String UserId = java.util.UUID.randomUUID().toString();

        //檢查是否有重複的email
        if(userService.selectByEmail(userRequest.getEmail()) == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else{
            Date now=new Date();
            userRequest.setUserId(UserId);
            userRequest.setCreateDate(now);
            userRequest.setLastModifyDate(now);
            userService.insert(userRequest);
            User NewUser = userService.selectByPrimaryKey(UserId);
            NewUser.setPassword(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(NewUser);
        }
    }


}
