package ce.mnu.todolist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ce.mnu.todolist.domain.RoomDTO;
import ce.mnu.todolist.domain.UserDTO;
import ce.mnu.todolist.repository.FriendRequest;
import ce.mnu.todolist.repository.FriendUser;
import ce.mnu.todolist.repository.MyTodo;
import ce.mnu.todolist.repository.ShareRoom;
import ce.mnu.todolist.repository.User;
import ce.mnu.todolist.service.FriendRequestService;
import ce.mnu.todolist.service.FriendUserService;
import ce.mnu.todolist.service.MyTodoService;
import ce.mnu.todolist.service.UserService;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/todo/*")
public class TodolistController {
	@Autowired
	private UserService userService;
	@Autowired
	private MyTodoService myTodoService;
	@Autowired
	private FriendRequestService friendRequestService;
	@Autowired
	private FriendUserService friendUserService;
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
	        return "redirect:/todo/homepage";
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
	//내 일정 관리
	@GetMapping("/my_todo")
	public String myTodo() {
	return "mytodo";
	}
	//내 일정 조회
	@PostMapping("/my_todo_process")
	public String myTodoProcess() {
	return "mytodo";
	}
	@GetMapping("/selected_date")
	public String myTodoProcess(String selectedDate, HttpSession session, Model model) {
	    User user = (User) session.getAttribute("loginUser");
	    if (user == null) {
	        return "redirect:/todo/login";
	    }
	    List<MyTodo> todos = new ArrayList<>();
	    if (selectedDate != null && !selectedDate.isEmpty()) {
	        todos = myTodoService.findMyTodosByEmailAndDate(user.getEmail(), selectedDate);
	    }
	    model.addAttribute("todos", todos);
	    model.addAttribute("selectedDate", selectedDate); // 날짜도 같이 전달
	    return "selecteddate";
	}
	//일정 등록
	@PostMapping("/add_todo")
	public String addTodo(String selectedDate, String todo, HttpSession session, Model model) {
	    User user = (User) session.getAttribute("loginUser");
	    if (user == null) {
	        return "redirect:/todo/login";
	    }
	    String email = user.getEmail();;
	    myTodoService.addTodo(email, selectedDate, todo);

	    return "redirect:/todo/selected_date?selectedDate=" + selectedDate;
	}
	//일정 삭제
	@PostMapping("/delete_todo")
	public String deleteTodo(Long id, String selectedDate, HttpSession session) {
	    User user = (User) session.getAttribute("loginUser");
	    if (user == null) {
	        return "redirect:/todo/login";
	    }
	    myTodoService.deleteTodo(id);
	    // 삭제 후 해당 날짜 일정 페이지로 리다이렉트
	    return "redirect:/todo/selected_date?selectedDate=" + selectedDate;
	}
	
	//일정 수정
	@GetMapping("/edit_todo_form")
	public String editTodoForm(Long id, String selectedDate, HttpSession session, Model model) {
	    User user = (User) session.getAttribute("loginUser");
	    if (user == null) return "redirect:/todo/login";
	    List<MyTodo> todos = myTodoService.findMyTodosByEmailAndDate(user.getEmail(), selectedDate);
	    model.addAttribute("todos", todos);
	    model.addAttribute("selectedDate", selectedDate);
	    model.addAttribute("editId", id); // 수정 중인 일정 id
	    return "selecteddate";
	}
	@PostMapping("/edit_todo")
	public String editTodo(Long id, String todo, String selectedDate, HttpSession session) {
	    User user = (User) session.getAttribute("loginUser");
	    if (user == null) return "redirect:/todo/login";
	    myTodoService.updateTodo(id, todo);
	    return "redirect:/todo/selected_date?selectedDate=" + selectedDate;
	}

	//공유 방목록
	@GetMapping("/share_room")
	public String sharRoom(Model model) {
		Iterable<ShareRoom> rooms = userService.getRoomAll();
		model.addAttribute("rooms",rooms);
	return "share_roomlist";
	}	
	//공유 일정 방 만들기
    @GetMapping("create_room")
    public String CreateRoomForm(HttpSession session) {
    	User user = (User) session.getAttribute("loginUser");
    	if (user == null) {
    	    return "redirect:/todo/login"; // 로그인 되지 않았으면 로그인 페이지로 리다이렉트
    	}
    	return "createroom";
    }
    @PostMapping("create_room")
    public String CreateRoom(RoomDTO room, HttpSession session, Model model) {
    	User user = (User) session.getAttribute("loginUser");
    	String email = user.getEmail();
    	// 중복 체크
        if (userService.isRoomNameExists(room.getRoomname())) {
            model.addAttribute("error", "중복되는 방이름입니다.");
            return "createroom";
        }
    	userService.save(room, email);//DB에 저장
    	return "redirect:/todo/share_room";
    }
    
    //공유 일정 조회
    @GetMapping("/share_todo")
    public String ShareTodo(String roomname){
    	return "sharetodo";
    }

	// 소셜(친구 목록)
    @GetMapping("/social")
    public String friendsList(Model model, HttpSession session) {
        User me = (User) session.getAttribute("loginUser");
        if (me == null) {
            return "redirect:/todo/homepage";
        }
        String myname = me.getName();
        List<FriendRequest> requests = friendRequestService.getRequestsForUser(myname);
        model.addAttribute("requests", requests);

        List<FriendUser> friends = friendUserService.getFriends(myname);
        model.addAttribute("friends", friends);
        return "friends";
    }
    //소셜(친구 삭제)
    @PostMapping("/delete_friend")
    public String deleteFriend(String friendname, HttpSession session) {
        User me = (User) session.getAttribute("loginUser");
        if (me == null) return "redirect:/todo/login";
        friendUserService.deleteFriend(me.getName(), friendname);
        return "redirect:/todo/social";
    }

    // 사용자 검색
    @GetMapping("/search_user")
    public String searchUser(HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/todo/homepage";
        }
        return "search_user";
    }
    // 사용자 검색 처리
    @PostMapping("/search_user")
    public String searchUserResult(String email, Model model, HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/todo/homepage";
        }
        User user = userService.findByEmail(email);
        if (user != null) {
            model.addAttribute("name", user.getName());
        } else {
            model.addAttribute("error", "해당 이메일로 등록된 사용자가 없습니다.");
        }
        return "search_user_done";
    }
    // 친구 요청 보내기
    @PostMapping("/send_friend_request")
    public String sendFriendRequest(String toUser, HttpSession session) {
        User me = (User) session.getAttribute("loginUser");
        if (me == null) return "redirect:/todo/login";
        friendRequestService.sendRequest(me.getName(), toUser);
        return "redirect:/todo/social";
    }
    // 친구 요청 수락
    @GetMapping("/accept_friend_request")
    public String acceptFriendRequest(Long id, HttpSession session) {
        User me = (User) session.getAttribute("loginUser");
        if (me == null) return "redirect:/todo/login";
        FriendRequest req = friendRequestService.findById(id);
        if (req != null && req.getToUser().equals(me.getName())) {
            friendRequestService.acceptRequest(id);
            friendUserService.addFriend(req.getFromUser(), req.getToUser());
        }
        return "redirect:/todo/social";
    }
    //친구 요청 거절
    @GetMapping("/reject_friend_request")
    public String rejectFriendRequest(Long id, HttpSession session) {
        User me = (User) session.getAttribute("loginUser");
        if (me == null) return "redirect:/todo/login";
        FriendRequest req = friendRequestService.findById(id);
        if (req != null && req.getToUser().equals(me.getName())) {
            friendRequestService.rejectRequest(id);
        }
        return "redirect:/todo/social";
    }
    
}