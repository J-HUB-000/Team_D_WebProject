package ce.mnu.todolist.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "site_user")
@Data
@NoArgsConstructor
public class User {
    @Id
    @Column(length = 100) // 이메일 길이에 맞게 조정
    private String email;
    @Column(length = 100, unique = true)
    private String name;
    private String passwd;
    private String phone;

    public User(String name, String email, String passwd, String phone) {
        this.name = name;
        this.email = email;
        this.passwd = passwd;
        this.phone = phone;
    }
}
