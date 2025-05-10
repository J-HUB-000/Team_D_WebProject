package ce.mnu.todolist.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "site_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nickname;
	private String phone;
	private String email;
	private String name;
	private String passwd;
	public User(String name, String email, 
			String passwd, String nickname, String phone) {
		this.name=name;
		this.email=email;
		this.passwd=passwd;
		this.nickname=nickname;
		this.phone=phone;
	}
}
