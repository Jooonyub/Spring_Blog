package com.hanghae.blog.controller;

import com.hanghae.blog.dto.UserRequestDto;
import com.hanghae.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    //로그인에 실패한경우
    @GetMapping("/user/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    //success and fail 자바스크립트쪽

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }

    // 회원 가입 피드백
    @PostMapping("/user/signup")
    public String registerUser(Model model, UserRequestDto requestDto) {
        try {
            userService.registerUser(requestDto);

            // 성공적으로 일을 다 마쳤다!
            return "redirect:/";
        } /*catch (NicknameDuplicationException e){
            model.addAttribute("error_msg", "중복됩니다.");

        } catch (NicknameLengthException e){
        } catch (NicknameRegexException e){
        } catch (PasswordLengthException e){
        } catch (PasswordContainNicknameException e){
        } catch (PasswordIncoincidenceException e){
        } */catch (Exception e){
            model.addAttribute("error", true);
            model.addAttribute("error_msg", e.getMessage());
        }



        return "signup";
    }

    /*
    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(SignupRequestDto requestDto) {
        userService.registerUser(requestDto);
        return "redirect:/";
    }

     */

    //쿼리 or 파라미터
    //세션

   //forbidden 페이지를 위한 UserController
    @GetMapping("/user/forbidden")
    public String forbidden() {
        return "forbidden";
    }

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(String code) {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        userService.kakaoLogin(code);

        return "redirect:/";
    }
}

