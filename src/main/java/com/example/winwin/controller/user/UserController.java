package com.example.winwin.controller.user;

import com.example.winwin.dto.user.UserDto;
import com.example.winwin.dto.user.UserPfpDto;
import com.example.winwin.service.mentor.LoginService;
import com.example.winwin.service.myPage.MyPageService;
import com.example.winwin.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/*")
public class UserController {

    private final UserService userService;
    private final LoginService loginService;
    private final MyPageService myPageService;

    @GetMapping
    public String loginModal(){
        return "user/loginModal";
    }

//    @GetMapping("/certification")
//    public String certification(){return "user/certification";}

    /* 로그인 */
    @PostMapping("/login")
    public RedirectView login(String userId, String userPassword, HttpServletRequest req){
        try {
            Long userNumber = userService.findUserNumber(userId, userPassword);
            UserDto userDto = userService.findUserInfo(userNumber);
            HttpSession session = req.getSession();
            session.setAttribute("userNumber", userNumber);
            session.setAttribute("userName", userDto.getUserName());
            session.setAttribute("userWing", userDto.getUserWing());
            session.setAttribute("userStatus", userDto.getUserStatus());
            session.setAttribute("userNickname", userDto.getUserNickname());


            Long mentorNumber = loginService.findMentorNumber(userId, userPassword);
            session.setAttribute("mentorNumber", mentorNumber);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new RedirectView("/user/login");
        }
        return new RedirectView("/main/main");
    }

    /* 로그아웃 */
    @GetMapping("/logout")
    public RedirectView logout(HttpServletRequest req){
        req.getSession().invalidate();
        return new RedirectView("/main/main");
    }

    @GetMapping("/resultId")
    public String resultId(){return "user/resultId";}

    @GetMapping("/resultPw")
    public String resultPw(){return "user/resultPw";}


}
