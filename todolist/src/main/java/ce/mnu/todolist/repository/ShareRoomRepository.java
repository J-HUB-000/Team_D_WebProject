package ce.mnu.todolist.repository;

import org.springframework.data.repository.CrudRepository;

public interface ShareRoomRepository extends CrudRepository<ShareRoom, Long> {
	boolean existsByRoomname(String roomname);
}
