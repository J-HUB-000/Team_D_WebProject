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
@Table(name = "share_friends")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareFriends {
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
private Long id;
private Long roomid;
private String name;
private String friendemail;
}
