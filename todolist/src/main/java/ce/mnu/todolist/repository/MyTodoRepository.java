package ce.mnu.todolist.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MyTodoRepository extends CrudRepository<MyTodo, Long> {
    List<MyTodo> findByEmailAndCallendardate(String email, String callendardate);
}

