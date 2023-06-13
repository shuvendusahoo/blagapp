package com.Blog.PayLoad;
import lombok.Data;
@Data
public class LoginDto {
    private String usernameOrEmail;
    private String password;
}
