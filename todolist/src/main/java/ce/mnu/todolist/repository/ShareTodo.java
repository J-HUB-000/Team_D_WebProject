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
@Table(name = "share_todo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareTodo {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	private Long roomid;
    private String email;
    private String callendardate;
    private String sharetodo;
}
