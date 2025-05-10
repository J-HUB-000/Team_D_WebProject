package ce.mnu.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ce.mnu.todolist.domain.SignupDTO;
import ce.mnu.todolist.repository.User;
import ce.mnu.todolist.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	//DTO 클래스에서 가져온 데이터를 SiteUserRepository클래스에 저장.
	public void save(SignupDTO dto) {
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

}

