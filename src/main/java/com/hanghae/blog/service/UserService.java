package com.hanghae.blog.service;

import com.hanghae.blog.dto.UserRequestDto;
import com.hanghae.blog.exception.*;
import com.hanghae.blog.model.User;
import com.hanghae.blog.model.UserRole;
import com.hanghae.blog.repository.UserRepository;
import com.hanghae.blog.security.UserDetailsImpl;
import com.hanghae.blog.security.kakao.KakaoOAuth2;
import com.hanghae.blog.security.kakao.KakaoUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    //관리자 권한 확인을 위한 토큰
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KakaoOAuth2 kakaoOAuth2;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, KakaoOAuth2 kakaoOAuth2) {
        // 다넣지 않고 고의누락중(나중에 어떤 문제가 생기는지 보기 위해
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.kakaoOAuth2 = kakaoOAuth2;
    }

    //회원가입 기능
    public void registerUser(UserRequestDto requestDto) throws Exception{
        String username = requestDto.getUsername();
        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            //throw new IllegalArgumentException("중복된 닉네임입니다.");
            throw new NicknameDuplicationException();
            //"중복된 닉네임입니다."
        }

        if (username.length() < 3) {
            //throw new IllegalArgumentException("닉네임은 최소 3자 이상이어야 합니다.");
            throw new NicknameLengthException();
            //"닉네임은 최소 3자 이상이어야 합니다."
            //System.err.println("FoolException이 발생했습니다.");
        }
        //정규표현식을 import하여 영어,숫자로만 되어있는지 검증
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]*$");
        Matcher matcher = pattern.matcher(username);
        if(!matcher.find()) {
            throw new NicknameRegexException();
            //"닉네임은 알파벳 대소문자, 숫자로 구성되어야 합니다."
        }
        // 패스워드 길이
        String originalpassword = requestDto.getPassword();
        if (originalpassword.length() < 4) {
            throw new PasswordLengthException();
            //"비밀번호는 최소 4자 이상이어야 합니다."
        }
        // 패스워드 인코딩
        //String originalpassword = requestDto.getPassword();
        if (originalpassword.contains(username)) {
            throw new PasswordContainNicknameException();
            //"비밀번호는 닉네임과 같은 값이 포함될 수 없습니다."
        }
        //비밀번호 한번더 입력하여 원래것과 일치하는지 확인하기
        String againpassword = requestDto.getAgainpassword();
        if (!originalpassword.contentEquals(againpassword)) {
            throw new PasswordIncoincidenceException();
            //"비밀번호 확인 입력값이 비밀번호와 일치하지 않습니다."
        }
        String password = passwordEncoder.encode(originalpassword);
        //String email = requestDto.getEmail();
        // 사용자 ROLE 확인
        UserRole role = UserRole.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new AdminAccessException();
                //"관리자 암호가 틀려 등록이 불가능합니다."

            }
            //관리자토큰이 올바르게 입력된 경우
            role = UserRole.ADMIN;
        }

        //User user = new User(username, password, email, role);
        User user = new User(username, password, role);
        userRepository.save(user);
    }

    /*
    //회원가입 기능
    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        if (username.length() < 3) {
            throw new IllegalArgumentException("닉네임은 최소 3자 이상이어야 합니다.");
            //System.err.println("FoolException이 발생했습니다.");
            //Window.
        }
        //정규표현식을 import하여 영어,숫자로만 되어있는지 검증
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]*$");
        Matcher matcher = pattern.matcher(username);
        if(!matcher.find()) {
            throw new IllegalArgumentException("닉네임은 알파벳 대소문자, 숫자로 구성되어야 합니다.");
        }
        // 패스워드 길이
        String originalpassword = requestDto.getPassword();
        if (originalpassword.length() < 4) {
            throw new IllegalArgumentException("비밀번호는 최소 4자 이상이어야 합니다.");
        }
        // 패스워드 인코딩
        String originalpassword = requestDto.getPassword();
        if (originalpassword.contains(username)) {
            throw new IllegalArgumentException("비밀번호는 닉네임과 같은 값이 포함될 수 없습니다.");
        }
        //비밀번호 한번더 입력하여 원래것과 일치하는지 확인하기
        String againpassword = requestDto.getAgainpassword();
        if (!originalpassword.contentEquals(againpassword)) {
            throw new IllegalArgumentException("비밀번호 확인 입력값이 비밀번호와 일치하지 않습니다.");
        }
        String password = passwordEncoder.encode(originalpassword);
        String email = requestDto.getEmail();
        // 사용자 ROLE 확인
        UserRole role = UserRole.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");

            }
            //관리자토큰이 올바르게 입력된 경우
            role = UserRole.ADMIN;
        }

        User user = new User(username, password, email, role);
        userRepository.save(user);
    }
    */


    //소셜로그인 기능
    public void kakaoLogin(String authorizedCode) {
        // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
        KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo(authorizedCode);
        Long kakaoId = userInfo.getId();
        String nickname = userInfo.getNickname();
        //String email = userInfo.getEmail();

        /*
        // 우리 DB 에서 회원 Id 와 패스워드
        // 회원 Id = 카카오 nickname
        String username = nickname;
        // 패스워드 = 카카오 Id + ADMIN TOKEN
        String password = kakaoId + ADMIN_TOKEN;
         */

        // DB 에 중복된 Kakao Id 가 있는지 확인
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);

        // 카카오 정보로 회원가입
        if (kakaoUser == null) {
            /*
            // 카카오 이메일과 동일한 이메일을 가진 회원이 있는지 확인
            User sameEmailUser = userRepository.findByEmail(email).orElse(null);
            if (sameEmailUser != null) {
                kakaoUser = sameEmailUser;
                // 카카오 이메일과 동일한 이메일 회원이 있는 경우
                // 카카오 Id 를 회원정보에 저장
                kakaoUser.setKakaoId(kakaoId);
                userRepository.save(kakaoUser);

            } else {

             */
                // 카카오 정보로 회원가입
                // username = 카카오 nickname
            String username = nickname;
            // password = 카카오 Id + ADMIN TOKEN
            String password = kakaoId + ADMIN_TOKEN;
            // 패스워드 인코딩
            String encodedPassword = passwordEncoder.encode(password);
            // ROLE = 사용자
            UserRole role = UserRole.USER;

            //kakaoUser = new User(username, encodedPassword, email, role, kakaoId);
            kakaoUser = new User(username, encodedPassword, role, kakaoId);
            userRepository.save(kakaoUser);

        /*
        // 로그인 처리
        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);
         */

                // 강제 로그인 처리
            UserDetailsImpl userDetails = new UserDetailsImpl(kakaoUser);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //}
        }
    }

    //로그인 검증
}
