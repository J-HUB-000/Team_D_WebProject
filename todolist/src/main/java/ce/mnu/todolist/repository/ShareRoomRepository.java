package ce.mnu.todolist.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ShareRoomRepository extends CrudRepository<ShareRoom, Long> {
	boolean existsByRoomname(String roomname);
	List<ShareRoom> findByEmail(String email);
	List<ShareRoom> findByRoomid(Long roomid);
}
