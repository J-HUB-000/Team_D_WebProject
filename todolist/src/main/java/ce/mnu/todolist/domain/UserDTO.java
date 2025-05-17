package ce.mnu.todolist.domain;

import lombok.Data;

@Data
public class UserDTO {
	String name;
	String email;
	String passwd;
	String nickname;
	String phone;
}
