package ce.mnu.todolist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ShareFriendsRepository extends CrudRepository<ShareFriends, Long>{
    @Query("select sf.friendemail from ShareFriends sf")
	List<String> findAllFriendEmails();
	@Query("select sf.roomid from ShareFriends sf")
	List<String> findAllRoomid();
	List<ShareFriends> findByFriendemail(String friendemail);
	void deleteByRoomid(Long roomid);
}
