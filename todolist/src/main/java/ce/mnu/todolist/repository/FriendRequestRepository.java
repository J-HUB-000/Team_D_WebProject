package ce.mnu.todolist.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long> {
    boolean existsByFromUserAndToUserAndStatus(String fromUser, String toUser, String status);
    List<FriendRequest> findByToUserAndStatus(String toUser, String status);
    List<FriendRequest> findByFromUserAndStatus(String fromUser, String status);
}

