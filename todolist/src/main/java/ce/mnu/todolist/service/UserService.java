package ce.mnu.todolist.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ce.mnu.todolist.domain.RoomDTO;
import ce.mnu.todolist.domain.UserDTO;
import ce.mnu.todolist.repository.*;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ShareRoomRepository shareRoomRepository;
	@Autowired
	private ShareTodoRepository shareTodoRepository;
	@Autowired
	private ShareFriendsRepository shareFriendsRepository;
	@Autowired
	private ShareFriendsRepository shareShareFriendsRepository;
	
	//DTO 클래스에서 가져온 데이터를 SiteUserRepository클래스에 저장.
	public void save(UserDTO dto) {
		User user = new User(dto.getName(), 
				dto.getEmail(), dto.getPasswd(),
				dto.getPhone());
		userRepository.save(user);
	}
	public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }
	public boolean isNameExists(String name) {
        return userRepository.existsByName(name);
    }
	public Iterable<User> getAll() {
		//SELECT * FROM site_user; 와 같은 의미
		return userRepository.findAll();
	}
	public User login(String email, String passwd) {
        return userRepository.findByEmailAndPasswd(email, passwd);
    }
	// 이메일에 해당하는 객체(투플) 가져오기
	public User findByEmail(String email) {
	    return userRepository.findByEmail(email);
	}
	// 이름에 해당하는 객체(투플) 가져오기
	public User findByUserName(String name) {
		return userRepository.findByName(name);
	}
	// 친구 이메일에 해당하는 객체(투플) 가져오기
	public List<ShareFriends> findByFriendEmail(String friendemail) {
		return shareFriendsRepository.findByFriendemail(friendemail);
	}
	
	//입력받은 공유방 정보 dto객체로 받아서 테이블에 저장하는 메서드
	public void save(RoomDTO dto, String email) {
		ShareRoom room = new ShareRoom();
		room.setEmail(email);
		room.setRoomname(dto.getRoomname());
		room.setRoomexp(dto.getRoomexp());
		shareRoomRepository.save(room);
	}
	//생성된 공유방들 전부 찾기
	public Iterable<ShareRoom> getRoomAll() {
		return shareRoomRepository.findAll();
	}
	public boolean isRoomNameExists(String roomname) {
	    return shareRoomRepository.existsByRoomname(roomname);
	}
	//공유방 삭제
	@Transactional
	public void deleteRoomAndShareTodosAndShareFriends(Long roomid) {
		shareTodoRepository.deleteByRoomid(roomid); // 일정 삭제
	    shareRoomRepository.deleteById(roomid);     // 방 삭제
	    shareShareFriendsRepository.deleteByRoomid(roomid); //공유받은 친구들도 삭제
	}
	//공유방 생성한 호스트 이메일 찾기
	public List<ShareRoom> getRoomsByOwnerEmail(String email) {
	    return shareRoomRepository.findByEmail(email);
	}
	// 공유방의 roomId를 찾기
	public List<ShareRoom> getRoomsByRoomid(Long roomid) {
		return shareRoomRepository.findByRoomid(roomid);
	}
	// 공유한 친구로부터 roomId 찾기
	public List<String> getAllRoomids() {
		return shareFriendsRepository.findAllRoomid();
	}
	
	// 공유일정 친구 공유(초대)
	public void save(Long roomid, String name, String email) {
		ShareFriends shareFriends = new ShareFriends();
		shareFriends.setRoomid(roomid);
		shareFriends.setName(name);
		shareFriends.setFriendemail(email);
		shareFriendsRepository.save(shareFriends);
	}
	// 공유한 모든 친구의 이메일 가져오기
	public List<String> getAllFriendEmails() {
		return shareFriendsRepository.findAllFriendEmails();
	}
	
}

