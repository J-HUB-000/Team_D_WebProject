package ce.mnu.todolist.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ce.mnu.todolist.domain.RoomDTO;
import ce.mnu.todolist.domain.UserDTO;
import ce.mnu.todolist.repository.ShareRoom;
import ce.mnu.todolist.repository.ShareRoomRepository;
import ce.mnu.todolist.repository.ShareTodoRepository;
import ce.mnu.todolist.repository.User;
import ce.mnu.todolist.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ShareRoomRepository shareRoomRepository;
	@Autowired
	private ShareTodoRepository shareTodoRepository;
	
	//DTO 클래스에서 가져온 데이터를 SiteUserRepository클래스에 저장.
	public void save(UserDTO dto) {
		User user = new User(dto.getName(), 
				dto.getEmail(), dto.getPasswd(),
				dto.getNickname(), dto.getPhone());
		userRepository.save(user);
	}
	public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }
	public Iterable<User> getAll() {
		//SELECT * FROM site_user; 와 같은 의미
		return userRepository.findAll();
	}
	public User login(String email, String passwd) {
        return userRepository.findByEmailAndPasswd(email, passwd);
    }
	public User findByEmail(String email) {
	    return userRepository.findByEmail(email);
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
	public void deleteRoomAndShareTodos(Long roomid) {
		shareTodoRepository.deleteByRoomid(roomid); // 일정 삭제
	    shareRoomRepository.deleteById(roomid);     // 방 삭제
	}
	//공유방 생성한 호스트 이메일 찾기
	public List<ShareRoom> getRoomsByOwnerEmail(String email) {
	    return shareRoomRepository.findByEmail(email);
	}

}

