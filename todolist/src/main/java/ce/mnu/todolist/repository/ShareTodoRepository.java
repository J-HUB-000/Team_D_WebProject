package ce.mnu.todolist.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ShareTodoRepository extends CrudRepository<ShareTodo, Long>  {
	List<ShareTodo> findByRoomid(Long roomid);
	void deleteByRoomid(Long roomid);
	List<ShareTodo> findByRoomidAndCallendardate(Long roomid, String callendardate);
}
