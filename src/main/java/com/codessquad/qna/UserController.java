package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @PostMapping("/user/create")
    public String create(String userId, String password, String name, String email){
        System.out.println("userID: "+userId);
        System.out.println("password: "+password);
        System.out.println("name: "+name);
        System.out.println("email: "+email);
        return "index";
    }
}
