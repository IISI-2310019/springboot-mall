package tw.com.mall.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/*
    * 這個類別是用來接收使用者註冊的請求
    * 這個類別的屬性是使用者的email,密碼和使用者名稱
 */

public class UserRegisterRequest {

    @NotBlank
    @Email
    String email;

    @NotBlank
    //@JsonIgnore
    String password;

    @NotBlank
    String userName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }
}
