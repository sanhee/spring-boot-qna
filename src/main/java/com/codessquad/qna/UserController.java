package com.codessquad.qna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<User> userList = new ArrayList<>();

    @GetMapping("/user/form.html")
    private String registerDisplay(){
        return "user/form";
    }
    @PostMapping("/user/create")
    private String register(User user){
        userList.add(user);
        logger.info(user.toString());
        return "redirect:/users";
    }
    @GetMapping("/users")
    private String getMemberList(Model model){
        model.addAttribute("users",userList);
        return "user/list";
    }

}
