package ce.mnu.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ce.mnu.todolist.domain.SignupDTO;
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
	    session.invalidate(); // 세션 전체 삭제 (로그아웃)
	    return "redirect:/todo/homepage"; // 메인 페이지로 이동
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
	@GetMapping("/signup")
	public String signup() {
		return "signup_input";
	}
	@PostMapping("/signup")
	public String signup(SignupDTO user) {
		userService.save(user);
		return "signup_done";
	}
}