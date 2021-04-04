package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import com.codessquad.qna.utils.HttpSessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("create")
    private String save(User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    private String getMemberList(Model model) {
        model.addAttribute("users", userService.getList());
        return "user/list";
    }

    @GetMapping("{primaryKey}")
    private String displayProfile(@PathVariable("primaryKey") Long targetKey, Model model) {
        model.addAttribute("users", userService.getById(targetKey));
        return "user/profile";
    }

    @GetMapping("{primaryKey}/form") //m 개인정보 수정
    private String changeMemberInfo(@PathVariable("primaryKey") Long targetKey, Model model, HttpSession session) {
        userService.authenticateOfId(userService.getById(targetKey), HttpSessionUtils.getLoginUserOf(session));
        model.addAttribute("users", HttpSessionUtils.getLoginUserOf(session));
        return "user/updateForm";
    }

    @PutMapping("{primaryKey}/update")
    private String updateMemberList(@PathVariable Long primaryKey, User updateUserData, Model model) {
        userService.update(primaryKey, updateUserData);
        model.addAttribute("users", userService.getList());
        return "redirect:/users";
    }

    @PostMapping("login")
    public String login(String userId, String password, HttpSession session) {
        userService.checkValidOfLogin(userId, password); //m null 일 경우 내부에서 예외처리됨.
        HttpSessionUtils.setAttribute(session, userService.getById(userId));
        return "redirect:/";
    }

    @GetMapping("logout") // 로그아웃은 주로 포스트맵핑을 사용한다고 하는데, 아직 잘 모르겠어서 변경하지 않음.
    private String logout(HttpSession session) {
        HttpSessionUtils.removeAttribute(session);
        return "redirect:/";
    }

}
