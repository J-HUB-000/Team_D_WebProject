package ce.mnu.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ce.mnu.todolist.domain.SignupDTO;
import ce.mnu.todolist.service.UserService;

@Controller
@RequestMapping("/todo/*")
public class TodolistController {
	@Autowired
	private UserService userService;
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
