package ce.mnu.todolist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ce.mnu.todolist.domain.UserDTO;
import ce.mnu.todolist.repository.User;
import ce.mnu.todolist.service.UserService;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/todo/*")
public class TodolistController {
	@Autowired
	private UserService userService;
	@GetMapping("/homepage")
	public String homepage() {
		return "homepage";
	}
	@GetMapping("/login")
    public String login() {
        return "login";
    }
	@GetMapping("/logout")
	public String logout(HttpSession session) {
	    session.invalidate(); 					// 세션 전체 삭제 (로그아웃)
	    return "redirect:/todo/homepage"; 		// 메인 페이지로 이동
	}
	@GetMapping("/userinfo")
	public String userInfo(HttpSession session, Model model) {
	    User user = (User) session.getAttribute("loginUser");
	    if (user == null) {
	        return "redirect:/todo/login";
	    }
	    model.addAttribute("user", user);
	    return "userinfo";
	}
    @PostMapping("/login")
    public String login(String email, String passwd, Model model, HttpSession session) {
        User user = userService.login(email, passwd);
        if (user != null) {
            // 로그인 성공: 세션에 사용자 정보 저장
            session.setAttribute("loginUser", user);
            return "redirect:/todo/homepage";
        } else {
            // 로그인 실패: 에러 메시지 전달
            model.addAttribute("error", "이메일 또는 비밀번호가 일치하지 않습니다.");
            return "login";
        }
    }
    // 비밀번호 찾기 
    @GetMapping("/findpasswd")
    public String findPasswdForm() {
        return "findpasswd";
    }
    // 비밀번호 찾기 처리
    @PostMapping("/findpasswd")
    public String findPasswd(String email, Model model) {
        User user = userService.findByEmail(email);
        if (user != null) {
            model.addAttribute("passwd", user.getPasswd());
        } else {
            model.addAttribute("error", "해당 이메일로 등록된 사용자가 없습니다.");
        }
        return "findpasswd_done";
    }
    // 회원가입
	@GetMapping("/signup")
	public String signup() {
		return "signup_input";
	}
	// 회원가입 처리
	@PostMapping("/signup")
	public String signup(UserDTO user, Model model) {
	    if (userService.isEmailExists(user.getEmail())) {
	        model.addAttribute("error", "이미 등록된 이메일입니다.");
	        return "signup_input";
	    }
	    userService.save(user);
	    model.addAttribute("UserDTO", user); // 가입 완료 페이지에서 이름 출력하려면 필요
	    return "signup_done";
	}
	//소셜(친구 목록)
	@GetMapping("/social")
	public String friendsList(Model model, HttpSession session) {
	    // 예시: 로그인한 사용자의 친구 목록을 가져오는 로직(추후 DB연동)
	    List<User> friends = new ArrayList<>();
	    // friends.add(new User(...)); // 임시 데이터
	    model.addAttribute("friends", friends);
	    return "friends";
	}

	//사용자 검색
	@GetMapping("/search_user")
	public String searchUser() {
		return "search_user";
	}
	//사용자 검색 처리
	@PostMapping("/search_user")
	public String searchUserResult(String email, Model model) {
        User user = userService.findByEmail(email);
        if (user != null) {
            model.addAttribute("name", user.getName());
        } else {
            model.addAttribute("error", "해당 이메일로 등록된 사용자가 없습니다.");
        }
		return "search_user_done";
	}
}