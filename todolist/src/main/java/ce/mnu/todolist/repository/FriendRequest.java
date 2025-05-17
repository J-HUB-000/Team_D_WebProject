package ce.mnu.todolist.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "friend_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequest {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String fromUser;   // 요청 보낸 사람
    @Column(nullable=false)
    private String toUser;     // 요청 받는 사람
    @Column(nullable=false)
    private String status;     // "PENDING", "ACCEPTED", "REJECTED"
}

